package io.fulflix.order.application.strategy;

import io.fulflix.core.web.principal.Role;
import io.fulflix.infra.client.delivery.DeliveryClient;
import io.fulflix.infra.client.delivery.DeliveryRequest;
import io.fulflix.infra.client.exception.HubErrorCode;
import io.fulflix.infra.client.exception.HubException;
import io.fulflix.infra.client.hub.HubClient;
import io.fulflix.infra.client.hub.HubResponse;
import io.fulflix.infra.client.product.ProductDetailResponse;
import io.fulflix.order.api.dto.AdminCreateOrderRequest;
import io.fulflix.order.api.dto.CreateOrderResponse;
import io.fulflix.order.api.dto.ReceiverCreateOrderRequest;
import io.fulflix.order.api.dto.SupplierCreateOrderRequest;
import io.fulflix.order.application.OrderCreateStrategy;
import io.fulflix.order.application.validator.OrderValidator;
import io.fulflix.order.domain.Order;
import io.fulflix.order.domain.OrderStatus;
import io.fulflix.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterAdminCreateOrder implements OrderCreateStrategy {
    private final OrderRepo orderRepo;
    private final OrderValidator orderValidator;
    private final DeliveryClient deliveryClient;
    private final HubClient hubClient;

    @Override
    @Transactional
    public CreateOrderResponse AdminCreateOrder(AdminCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        // 상품, 업체 유효성 검사
        ProductDetailResponse productResponse = orderValidator.checkProductExistForAdmin(createOrderRequest.getProductId());
        orderValidator.checkCompanyExistForAdmin(createOrderRequest.getSupplierId(), "SUPPLIER");
        orderValidator.checkCompanyExistForAdmin(createOrderRequest.getReceiverId(), "RECEIVER");
        OrderStatus orderStatus = orderValidator.validateStockAvailabilityForAdmin(productResponse, createOrderRequest.getOrderQuantity());

        // 출발/도착 허브 유효성 검사
        HubResponse departureHub = hubClient.getHubById(createOrderRequest.getDeliveryRequest().getDepartureHubId());
        if (departureHub == null || departureHub.getId() == null) {
            throw new HubException(HubErrorCode.HUB_NOT_FOUND);
        }

        HubResponse arrivalHub = hubClient.getHubById(createOrderRequest.getDeliveryRequest().getArrivalHubId());
        if (arrivalHub == null || arrivalHub.getId() == null) {
            throw new HubException(HubErrorCode.HUB_NOT_FOUND);
        }

        // 주문 생성
        Order order = Order.builder()
                .supplierId(productResponse.getCompanyId())
                .receiverId(createOrderRequest.getReceiverId())
                .productId(createOrderRequest.getProductId())
                .orderQuantity(createOrderRequest.getOrderQuantity())
                .orderStatus(orderStatus)
                .build();

        orderRepo.save(order);

        if (orderStatus == OrderStatus.SUCCESS) {
            // 재고 감소
            orderValidator.reduceStock(createOrderRequest.getProductId(), createOrderRequest.getOrderQuantity());

            // 배송 생성
            DeliveryRequest deliveryRequest = DeliveryRequest.builder()
                    .orderId(order.getId())  // 주문 ID
                    .departureHubId(departureHub.getId())  // 출발 허브 ID
                    .arrivalHubId(arrivalHub.getId())  // 도착 허브 ID
                    .deliveryAddress(arrivalHub.getAddress())  // 배송 주소
                    .recipient(createOrderRequest.getDeliveryRequest().getRecipient())  // 수취인
                    .recipientSlackId(createOrderRequest.getDeliveryRequest().getRecipientSlackId())  // 수취인 Slack ID
                    .build();

            log.info("배송 요청 데이터: {}", deliveryRequest);
            deliveryClient.createDelivery(deliveryRequest);  // 배송 생성 API 호출
        }

        // 주문 상태 반환 (재고O - SUCCESS / 재고X - FAIL)
        return new CreateOrderResponse(orderStatus);
    }

    @Override
    public CreateOrderResponse SupplierCreateOrder(SupplierCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        return null;
    }

    @Override
    public CreateOrderResponse ReceiverCreateOrder(ReceiverCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        return null;
    }
}
