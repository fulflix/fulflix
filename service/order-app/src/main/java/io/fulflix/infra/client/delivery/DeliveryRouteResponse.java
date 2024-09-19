package io.fulflix.infra.client.delivery;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryRouteResponse {
    private Long id;
    private Long deliveryId;
    private Integer sequence;
    private Long departureHubId;
    private Long arrivalHubId;
    private Double estimatedDistance;
    private Long estimatedDuration;
    private String status;
}
