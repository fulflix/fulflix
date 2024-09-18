package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.CreateOrderRequest;
import io.fulflix.order.api.dto.CreateOrderResponse;

public interface OrderCreateStrategy {
    CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest, Long currentUser, Role role);
    boolean isMatched(Role role);
}
