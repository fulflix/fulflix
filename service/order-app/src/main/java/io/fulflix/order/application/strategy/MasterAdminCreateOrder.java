package io.fulflix.order.application.strategy;

import io.fulflix.common.web.principal.Role;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterAdminCreateOrder implements OrderCreateStrategy {
    private final OrderRepo orderRepo;
    private final OrderValidator orderValidator;

    @Override
    public CreateOrderResponse AdminCreateOrder(AdminCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        ProductDetailResponse productResponse = orderValidator.checkProductExistForAdmin(createOrderRequest.getProductId());
        orderValidator.checkCompanyExistForAdmin(createOrderRequest.getSupplierId(), "SUPPLIER");
        orderValidator.checkCompanyExistForAdmin(createOrderRequest.getReceiverId(), "RECEIVER");
        OrderStatus orderStatus = orderValidator.validateStockAvailabilityForAdmin(productResponse, createOrderRequest.getOrderQuantity());

        Order order = Order.builder()
                .supplierId(productResponse.getCompanyId())
                .receiverId(createOrderRequest.getReceiverId())
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
    public CreateOrderResponse SupplierCreateOrder(SupplierCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        return null;
    }

    @Override
    public CreateOrderResponse ReceiverCreateOrder(ReceiverCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        return null;
    }
}
