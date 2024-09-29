package io.fulflix.order.api.dto;

import io.fulflix.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private OrderStatus orderStatus; // PENDING
}
