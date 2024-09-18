package io.fulflix.delivery.deliveryroute.application;

import io.fulflix.delivery.delivery.domain.Delivery;
import io.fulflix.delivery.delivery.domain.DeliveryRepository;
import io.fulflix.delivery.delivery.exception.DeliveryErrorCode;
import io.fulflix.delivery.delivery.exception.DeliveryException;
import io.fulflix.delivery.deliveryroute.api.dto.DeliveryRouteResponse;
import io.fulflix.delivery.deliveryroute.api.dto.DeliveryRouteUpdate;
import io.fulflix.delivery.deliveryroute.domain.DeliveryRoute;
import io.fulflix.delivery.deliveryroute.domain.DeliveryRouteRepo;
import io.fulflix.delivery.deliveryroute.exception.DeliveryRouteErrorCode;
import io.fulflix.delivery.deliveryroute.exception.DeliveryRouteException;
import io.fulflix.infra.client.HubRouteClient;
import io.fulflix.infra.client.dto.ShortestPathRequest;
import io.fulflix.infra.client.dto.ShortestPathResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

//    - **생성**: 주문 생성 시 자동으로 생성
//    - **수정**: 마스터 관리자, 해당 허브 관리자, 그리고 해당 배송 담당자만 가능
//    - **조회 및 검색**: 모든 로그인 사용자가 가능, 단 배송 담당자는 자신이 담당하는 배송만 조회 가능


    // 배송 경로 생성
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

    // 배송 경로 단건 조회 (모든 로그인 사용자)
    public DeliveryRouteResponse getDeliveryRoute(Long id) {
        DeliveryRoute deliveryRoute = findDeliveryRouteById(id);
        return DeliveryRouteResponse.fromEntity(deliveryRoute);
    }

    // 배송경로 전체 조회 (모든 로그인 사용자)
    public Page<DeliveryRouteResponse> getAllDeliveryRoute(Pageable pageable) {
        Page<DeliveryRoute> deliveryRoutes = deliveryRouteRepo.findAll(pageable);
        return deliveryRoutes.map(DeliveryRouteResponse::fromEntity);
    }

    // 배송 경로 상태 변경
    @Transactional
    public DeliveryRouteResponse updateDeliveryRouteStatus(DeliveryRouteUpdate deliveryRouteUpdate) {
        DeliveryRoute deliveryRoute = findDeliveryRouteById(deliveryRouteUpdate.id());
        deliveryRoute.updateStatus(deliveryRouteUpdate.status());
        return DeliveryRouteResponse.fromEntity(deliveryRoute);
    }

    // 배송 경로 삭제
    @Transactional
    public void deleteDeliveryRoute(Long id) {
        DeliveryRoute deliveryRoute = findDeliveryRouteById(id);
        deliveryRoute.delete();
    }





    private Delivery findDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryException(DeliveryErrorCode.DELIVERY_NOT_FOUND));
    }

    private DeliveryRoute findDeliveryRouteById(Long id) {
        return deliveryRouteRepo.findById(id)
                .orElseThrow(() -> new DeliveryRouteException(DeliveryRouteErrorCode.DELIVERY_ROUTE_NOT_FOUND));
    }

}
