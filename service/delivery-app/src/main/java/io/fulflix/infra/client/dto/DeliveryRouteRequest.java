package io.fulflix.infra.client.dto;

public record DeliveryRouteRequest(
        Integer sequence,
        Long departureHubId,
        Long arrivalHubId,
        Double estimatedDistance,
        Long estimatedDuration
) {
}