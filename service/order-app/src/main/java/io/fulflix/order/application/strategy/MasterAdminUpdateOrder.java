package io.fulflix.order.application.strategy;

import io.fulflix.common.web.principal.Role;
import io.fulflix.infra.client.product.ProductClient;
import io.fulflix.order.api.dto.ReduceStockRequest;
import io.fulflix.order.api.dto.RestoreStockRequest;
import io.fulflix.order.api.dto.UpdateOrderRequest;
import io.fulflix.order.application.OrderUpdateStrategy;
import io.fulflix.order.domain.Order;
import io.fulflix.order.domain.OrderStatus;
import io.fulflix.order.exception.OrderErrorCode;
import io.fulflix.order.exception.OrderException;
import io.fulflix.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MasterAdminUpdateOrder implements OrderUpdateStrategy {
    private final OrderRepo orderRepo;
    private final ProductClient productClient;

    @Override
    @Transactional
    public void updateOrder(Long id, UpdateOrderRequest updateOrderRequest, Long currentUser, Role role) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (order.getOrderStatus() == OrderStatus.SUCCESS && !order.isDeleted()) {
            int currentOrderQuantity = order.getOrderQuantity(); // 기존 주문 수량
            int newOrderQuantity = updateOrderRequest.getOrderQuantity(); // 업데이트할 주문 수량

            // 새로운 주문 수량이 기존 주문 수량보다 많으면 재고 차감
            if (newOrderQuantity > currentOrderQuantity) {
                int difference = newOrderQuantity - currentOrderQuantity;
                ReduceStockRequest reduceStockRequest = new ReduceStockRequest(difference);
                productClient.reduceStock(order.getProductId(), reduceStockRequest);
            }
            // 새로운 주문 수량이 기존 주문 수량보다 적으면 재고 복원
            else if (newOrderQuantity < currentOrderQuantity) {
                int difference = currentOrderQuantity - newOrderQuantity;
                RestoreStockRequest restoreStockRequest = new RestoreStockRequest(difference);
                productClient.restoreStock(order.getProductId(), restoreStockRequest);
            }

            order.updateOrderQuantity(newOrderQuantity);
        }
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isMasterAdmin();
    }
}
