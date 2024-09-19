package io.fulflix.hub.hubroute.application;

import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.hub.domain.HubRepository;
import io.fulflix.hub.hubroute.domain.HubRoute;
import io.fulflix.hub.hubroute.domain.HubRouteRepository;
import io.fulflix.hub.infra.naver.application.NaverDirectionsService;
import io.fulflix.hub.infra.naver.dto.RouteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HubRouteGenerator {
    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;
    private final NaverDirectionsService naverDirectionsService;

    // 거리 150km 제한
    private static final double MAX_DISTANCE = 150000.0;

    // TODO 허브 경로 생성시 Naver API를 너무 많이 찌르는 문제
    @Transactional
    public void generateHubRoutes() {
        List<Hub> hubs = hubRepository.findAll();
        List<HubRoute> hubRoutes = new ArrayList<>();

        for (int i = 0; i < hubs.size(); i++) {
            Hub departureHub = hubs.get(i);
            for (int j = i + 1; j < hubs.size(); j++) {
                Hub arrivalHub = hubs.get(j);

                RouteInfo routeInfo = naverDirectionsService.getRouteInfo(departureHub, arrivalHub);

                if (routeInfo.distance() >= MAX_DISTANCE) {
                    // 거리 조건이 만족되지 않으면 다음 조건으로 넘어감
                    continue;
                }
                
                // 출발 허브 -> 도착 허브 엣지
                HubRoute hubRoute = HubRoute.builder()
                        .departureHub(departureHub)
                        .arrivalHub(arrivalHub)
                        .duration(routeInfo.duration())
                        .distance(routeInfo.distance())
                        .build();

                // 도착 허브 -> 출발 허브 엣지
                HubRoute reverseRoute = HubRoute.builder()
                        .departureHub(arrivalHub)
                        .arrivalHub(departureHub)
                        .duration(routeInfo.duration())
                        .distance(routeInfo.distance())
                        .build();

                // 양방향 경로를 추가
                hubRoutes.add(hubRoute);
                hubRoutes.add(reverseRoute);
            }
        }
        hubRouteRepository.saveAll(hubRoutes);
    }
}
