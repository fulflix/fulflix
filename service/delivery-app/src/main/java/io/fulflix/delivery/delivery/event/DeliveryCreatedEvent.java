package io.fulflix.delivery.delivery.event;

public record DeliveryCreatedEvent(
        Long deliveryId,
        Long orderId) {
}
