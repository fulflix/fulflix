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
public class HubCompanyRetrieve implements OrderRetrieveStrategy {
    private final OrderRepo orderRepo;

    @Override
    public Page<OrderDetailResponse> retrieveOrders(Integer orderQuantity, Pageable pageable, Long currentUser, Role role) {
        if (hasQuery(orderQuantity)) {
            return orderRepo.findByReceiverIdAndOrderQuantityGreaterThanEqual(currentUser, orderQuantity, pageable)
                    .map(OrderDetailResponse::fromEntity);
        }

        return orderRepo.findByReceiverId(currentUser, pageable).map(OrderDetailResponse::fromEntity);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubCompany();
    }

    @Override
    public boolean hasQuery(Integer orderQuantity) {
        return orderQuantity != null && orderQuantity > 0;
    }
}
