package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.UpdateOrderRequest;

public interface OrderUpdateStrategy {
    void updateOrder(Long id, UpdateOrderRequest updateOrderRequest, Long currentUser, Role role);
    boolean isMatched(Role role);
}
