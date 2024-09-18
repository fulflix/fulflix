package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.OrderDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRetrieveStrategy {
    Page<OrderDetailResponse> retrieveOrders(
            Integer orderQuantity,
            Pageable pageable,
            Long currentUser,
            Role role
    );

    boolean isMatched(Role role);
    boolean hasQuery(Integer orderQuantity);
}
