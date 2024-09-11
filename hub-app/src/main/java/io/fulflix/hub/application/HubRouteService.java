package io.fulflix.hub.application;

import io.fulflix.hub.application.dto.HubRouteCreateDto;
import io.fulflix.hub.application.dto.HubRouteResponseDto;
import io.fulflix.hub.domain.Hub;
import io.fulflix.hub.domain.HubRoute;
import io.fulflix.hub.repository.HubRepository;
import io.fulflix.hub.repository.HubRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubRouteService {
    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;


    // 허브 경로 생성
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateDto dto) {
        Hub departureHub = hubRepository.findById(dto.getDepartureHubId())
                .orElseThrow(() -> new IllegalArgumentException("출발 허브가 존재하지 않습니다: " + dto.getDepartureHubId()));
        Hub arrivalHub = hubRepository.findById(dto.getArrivalHubId())
                .orElseThrow(() -> new IllegalArgumentException("도착 허브가 존재하지 않습니다: " + dto.getArrivalHubId()));

        HubRoute hubRoute = HubRoute.builder()
                .departureHub(departureHub)
                .arrivalHub(arrivalHub)
                .duration(dto.getDuration())
                .build();

        HubRoute savedHubRoute = hubRouteRepository.save(hubRoute);
        return mapToDto(savedHubRoute);
    }

    // entity -> dto
    private HubRouteResponseDto mapToDto(HubRoute hubRoute) {
        HubRouteResponseDto dto = new HubRouteResponseDto();
        dto.setId(hubRoute.getId());
        dto.setDepartureHubId(hubRoute.getDepartureHub().getId());
        dto.setArrivalHubId(hubRoute.getArrivalHub().getId());
        dto.setDuration(hubRoute.getDuration());
        dto.setRoute(hubRoute.getRoute());
        return dto;
    }

}
