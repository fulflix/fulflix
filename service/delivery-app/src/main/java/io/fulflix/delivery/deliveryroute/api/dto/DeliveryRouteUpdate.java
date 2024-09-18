package io.fulflix.delivery.deliveryroute.api.dto;

import io.fulflix.delivery.deliveryroute.domain.DeliveryRouteStatus;

public record DeliveryRouteUpdate(
        Long id,
        DeliveryRouteStatus status
) {
}
