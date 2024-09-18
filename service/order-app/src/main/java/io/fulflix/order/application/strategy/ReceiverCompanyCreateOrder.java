package io.fulflix.order.application.strategy;

import io.fulflix.common.web.principal.Role;
import io.fulflix.infra.client.product.ProductResponse;
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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiverCompanyCreateOrder implements OrderCreateStrategy {
    private final OrderRepo orderRepo;
    private final OrderValidator orderValidator;

    @Override
    public CreateOrderResponse ReceiverCreateOrder(ReceiverCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        ProductResponse productResponse = orderValidator.checkProductExistForCompany(createOrderRequest.getProductId());

        // TODO 생산업체 조회 예외처리

        OrderStatus orderStatus = orderValidator.validateStockAvailabilityForCompany(productResponse, createOrderRequest.getOrderQuantity());

        Order order = Order.builder()
                .supplierId(createOrderRequest.getSupplierId())
                .receiverId(currentUser)
                .productId(createOrderRequest.getProductId())
                .orderQuantity(createOrderRequest.getOrderQuantity())
                .orderStatus(orderStatus)
                .build();

        if (orderStatus == OrderStatus.SUCCESS) {
            orderValidator.reduceStock(createOrderRequest.getProductId(), createOrderRequest.getOrderQuantity());
        }

        orderRepo.save(order);

        return new CreateOrderResponse(orderStatus);
    }

    @Override
    public CreateOrderResponse AdminCreateOrder(AdminCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        return null;
    }

    @Override
    public CreateOrderResponse SupplierCreateOrder(SupplierCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        return null;
    }
}
