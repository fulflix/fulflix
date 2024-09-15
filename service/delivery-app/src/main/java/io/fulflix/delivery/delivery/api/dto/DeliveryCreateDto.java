package io.fulflix.delivery.delivery.api.dto;

public record DeliveryCreateDto(
    Long orderId,
    Long departureHubId,
    Long arrivalHubId,
    String deliveryAddress,
    String recipient,
    String recipientSlackId
) { }