package io.fulflix.delivery.deliveryroute.api.dto;

import io.fulflix.delivery.deliveryroute.domain.DeliveryRoute;

public record DeliveryRouteResponse(
        Long id,
        Long deliveryId,
        Integer sequence,
        Long departureHubId,
        Long arrivalHubId,
        Double estimatedDistance,
        Long estimatedDuration
) {
    public static DeliveryRouteResponse fromEntity(DeliveryRoute deliveryRoute) {
        return new DeliveryRouteResponse(
                deliveryRoute.getId(),
                deliveryRoute.getDelivery().getId(),
                deliveryRoute.getSequence(),
                deliveryRoute.getDepartureHubId(),
                deliveryRoute.getArrivalHubId(),
                deliveryRoute.getEstimatedDistance(),
                deliveryRoute.getEstimatedDuration()
        );
    }
}
