package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.AdminCreateOrderRequest;
import io.fulflix.order.api.dto.CreateOrderResponse;
import io.fulflix.order.api.dto.ReceiverCreateOrderRequest;
import io.fulflix.order.api.dto.SupplierCreateOrderRequest;

public interface OrderCreateStrategy {
    CreateOrderResponse AdminCreateOrder(AdminCreateOrderRequest createOrderRequest, Long currentUser, Role role);
    CreateOrderResponse SupplierCreateOrder(SupplierCreateOrderRequest createOrderRequest, Long currentUser, Role role);
    CreateOrderResponse ReceiverCreateOrder(ReceiverCreateOrderRequest createOrderRequest, Long currentUser, Role role);
}
