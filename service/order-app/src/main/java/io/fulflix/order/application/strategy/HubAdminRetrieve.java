package io.fulflix.order.application.strategy;

import io.fulflix.core.web.principal.Role;
import io.fulflix.order.api.dto.OrderDetailResponse;
import io.fulflix.order.application.OrderRetrieveStrategy;
import io.fulflix.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubAdminRetrieve implements OrderRetrieveStrategy {
    private final OrderRepo orderRepo;

    @Override
    public Page<OrderDetailResponse> retrieveOrders(Integer orderQuantity, Pageable pageable, Long currentUser, Role role) {
        if (hasQuery(orderQuantity)) {
            return orderRepo.findByOrderQuantityGreaterThanEqual(orderQuantity, pageable)
                    .map(OrderDetailResponse::fromEntity);
        }

        return orderRepo.findAll(pageable).map(OrderDetailResponse::fromEntity);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubAdmin();
    }

    @Override
    public boolean hasQuery(Integer orderQuantity) {
        return orderQuantity != null && orderQuantity > 0;
    }
}
