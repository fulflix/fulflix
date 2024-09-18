package io.fulflix.delivery.deliveryroute.application;

import io.fulflix.delivery.delivery.domain.Delivery;
import io.fulflix.delivery.delivery.domain.DeliveryRepository;
import io.fulflix.delivery.delivery.exception.DeliveryErrorCode;
import io.fulflix.delivery.delivery.exception.DeliveryException;
import io.fulflix.delivery.deliveryroute.api.dto.DeliveryRouteResponse;
import io.fulflix.delivery.deliveryroute.domain.DeliveryRoute;
import io.fulflix.delivery.deliveryroute.domain.DeliveryRouteRepo;
import io.fulflix.infra.client.HubClient;
import io.fulflix.infra.client.HubRouteClient;
import io.fulflix.infra.client.dto.ShortestPathRequest;
import io.fulflix.infra.client.dto.ShortestPathResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryRouteService {
    private final DeliveryRouteRepo deliveryRouteRepo;
    private final DeliveryRepository deliveryRepository;
    private final HubRouteClient hubRouteClient;


    @Transactional
    public List<DeliveryRouteResponse> createDeliveryRoute(Long deliveryId) {

        // 최단 경로 찾기
        Delivery delivery = findDeliveryById(deliveryId);
        Long start = delivery.getDepartureHubId();
        Long end = delivery.getArrivalHubId();
        ShortestPathRequest request = new ShortestPathRequest(start, end);

        // 외부 API에서 최단 경로 조회
        List<ShortestPathResponse> responseList = hubRouteClient.findShortestPath(request);

        // 최단 경로로 DeliveryRoute 생성
        List<DeliveryRoute> deliveryRouteList = responseList.stream()
                .map(response -> DeliveryRoute.fromShortestPathResponse(response, delivery)) // Delivery 전달
                .toList();

        // Delivery에 생성한 DeliveryRoute 추가
        deliveryRouteList.forEach(delivery::addDeliveryRoute);
        deliveryRepository.save(delivery);

        deliveryRouteRepo.saveAll(deliveryRouteList);

        return deliveryRouteList.stream()
                .map(DeliveryRouteResponse::fromEntity)
                .toList();
    }

    // 아이디로 배송 조회
    private Delivery findDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryException(DeliveryErrorCode.DELIVERY_NOT_FOUND));
    }

}
