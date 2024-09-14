package io.fulflix.delivery.delivery.api.dto;

import io.fulflix.delivery.delivery.domain.DeliveryStatus;

public record DeliveryUpdateDto(
    DeliveryStatus status,
    Long departureHubId,
    Long arrivalHubId,
    String deliveryAddress,
    String recipient,
    String recipientSlackId
) {}