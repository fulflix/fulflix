package io.fulflix.hub.hubroute.api.dto;

public record DeliveryRouteRequest(
        Integer sequence,
        Long departureHubId,
        Long arrivalHubId,
        Double estimatedDistance,
        Long estimatedDuration
) {
}