package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.OrderDetailResponse;

public interface OrderRetrieveByIdStrategy {
    OrderDetailResponse retrieveOrder(Long id, Long currentUser, Role role);
    boolean isMatched(Role role);
}
