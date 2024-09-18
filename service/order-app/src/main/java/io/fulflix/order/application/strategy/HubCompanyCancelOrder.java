package io.fulflix.order.application.strategy;

import io.fulflix.core.web.principal.Role;
import io.fulflix.infra.client.product.ProductClient;
import io.fulflix.order.api.dto.RestoreStockRequest;
import io.fulflix.order.application.OrderCancelStrategy;
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
public class HubCompanyCancelOrder implements OrderCancelStrategy {
    private final OrderRepo orderRepo;
    private final ProductClient productClient;

    @Override
    @Transactional
    public void cancelOrder(Long id, Long currentUser, Role role) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (order.getCreatedBy().equals(currentUser) &&
                order.getOrderStatus() == OrderStatus.SUCCESS && !order.isDeleted()) {
            RestoreStockRequest request = new RestoreStockRequest(order.getOrderQuantity());
            productClient.restoreStock(order.getProductId(), request);
        }

        order.delete();
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubCompany();
    }
}
