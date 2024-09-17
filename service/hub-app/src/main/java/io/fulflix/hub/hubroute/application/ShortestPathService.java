package io.fulflix.hub.hubroute.application;

import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.hub.domain.HubRepository;
import io.fulflix.hub.hubroute.api.dto.DeliveryRouteRequest;
import io.fulflix.hub.hubroute.domain.HubRoute;
import io.fulflix.hub.hubroute.domain.HubRouteRepository;
import lombok.RequiredArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShortestPathService {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;

    private final Graph<Hub, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

    // 엣지와 HubRoute 를 매핑
    private final Map<DefaultWeightedEdge, HubRoute> edgeToHubRouteMap = new HashMap<>();


    // 그래프 생성 : 노드에 허브 추가, 엣지에 허브루트 추가
    public void buildGraph() {
        List<Hub> hubs = hubRepository.findAll();
        List<HubRoute> hubRoutes = hubRouteRepository.findAll();

        // 모든 Hub를 그래프 노드로 추가
        for (Hub hub : hubs) {
            graph.addVertex(hub);
        }

        // HubRoute의 distance를 엣지 가중치로 설정
        for (HubRoute hubRoute : hubRoutes) {
            Hub departureHub = hubRoute.getDepartureHub();
            Hub arrivalHub = hubRoute.getArrivalHub();
            double distance = hubRoute.getDistance();

            DefaultWeightedEdge edge = graph.addEdge(departureHub, arrivalHub);
            if (edge != null) {
                // 가중치 설정
                graph.setEdgeWeight(edge, distance);

                // 엣지와 HubRoute를 매핑
                edgeToHubRouteMap.put(edge, hubRoute);
            }
        }
    }

    // 최단 경로 생성
    public List<DeliveryRouteRequest> findShortestPath(Long start, Long end) {
        DijkstraShortestPath<Hub, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);

        Hub startHub = findHubById(start);
        Hub endHub = findHubById(end);

        var path = dijkstraAlg.getPath(startHub, endHub);

        if (path != null) {
            return path.getEdgeList().stream()
                    .map(edge -> {
                        // 엣지에 매핑된 HubRoute로 DeliveryRouteRequest 생성
                        HubRoute hubRoute = edgeToHubRouteMap.get(edge);

                        return new DeliveryRouteRequest(
                                path.getEdgeList().indexOf(edge) + 1,       // 각 경로의 순서(sequence)
                                hubRoute.getDepartureHub().getId(),         // 출발 허브 ID
                                hubRoute.getArrivalHub().getId(),           // 도착 허브 ID
                                hubRoute.getDistance(),                     // 예상 거리
                                hubRoute.getDuration()                      // 예상 소요 시간
                        );
                    })
                    .toList();
        } else {
            System.out.println("No path found between " + startHub.getName() + " and " + endHub.getName());
            return List.of(); // 빈 리스트 반환
        }
    }

    public Hub findHubById(Long id) {
        return hubRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Hub not found with id " + id)
        );
    }
}