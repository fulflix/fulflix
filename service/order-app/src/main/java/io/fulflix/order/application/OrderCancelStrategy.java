package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;

public interface OrderCancelStrategy {
    void cancelOrder(Long id, Long currentUser, Role role);
    boolean isMatched(Role role);
}
