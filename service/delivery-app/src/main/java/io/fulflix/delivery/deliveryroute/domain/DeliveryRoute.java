package io.fulflix.delivery.deliveryroute.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "p_delivery_route_record")
@Getter
@NoArgsConstructor
public class DeliveryRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "delivery_id", nullable = false)
    private Long deliveryId;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "departure_hub_id", nullable = false)
    private Long departureHubId;

    @Column(name = "arrival_hub_id", nullable = false)
    private Long arrivalHubId;

    @Column(name = "estimated_distance", precision = 9, scale = 6, nullable = false)
    private BigDecimal estimatedDistance;

    @Column(name = "estimated_duration", nullable = false)
    private Integer estimatedDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private DeliveryRouteStatus status;

    private DeliveryRoute(Long deliveryId, Integer sequence, Long departureHubId, Long arrivalHubId,
                          BigDecimal estimatedDistance, Integer estimatedDuration, DeliveryRouteStatus status) {
        this.deliveryId = deliveryId;
        this.sequence = sequence;
        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;
        this.estimatedDistance = estimatedDistance;
        this.estimatedDuration = estimatedDuration;
        this.status = status;
    }

    public static DeliveryRoute create(Long deliveryId, Integer sequence, Long departureHubId, Long arrivalHubId,
                                       BigDecimal estimatedDistance, Integer estimatedDuration, DeliveryRouteStatus status) {
        return new DeliveryRoute(deliveryId, sequence, departureHubId, arrivalHubId, estimatedDistance, estimatedDuration, status);
    }
}
