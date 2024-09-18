package io.fulflix.order.application.strategy;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.OrderDetailResponse;
import io.fulflix.order.application.OrderRetrieveByIdStrategy;
import io.fulflix.order.domain.Order;
import io.fulflix.order.exception.OrderErrorCode;
import io.fulflix.order.exception.OrderException;
import io.fulflix.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubCompanyRetrieveById implements OrderRetrieveByIdStrategy {
    private final OrderRepo orderRepo;

    @Override
    public OrderDetailResponse retrieveOrder(Long id, Long currentUser, Role role) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getReceiverId().equals(currentUser)) {
            throw new OrderException(OrderErrorCode.UNAUTHORIZED_ACCESS);
        }

        return OrderDetailResponse.fromEntity(order);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubCompany();
    }
}
