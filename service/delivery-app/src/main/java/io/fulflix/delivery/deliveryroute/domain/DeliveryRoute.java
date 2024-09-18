package io.fulflix.delivery.deliveryroute.domain;

import io.fulflix.common.app.jpa.audit.Auditable;
import io.fulflix.delivery.delivery.domain.Delivery;
import io.fulflix.infra.client.dto.ShortestPathResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery_route_record")
@Getter
@NoArgsConstructor
public class DeliveryRoute extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "departure_hub_id", nullable = false)
    private Long departureHubId;

    @Column(name = "arrival_hub_id", nullable = false)
    private Long arrivalHubId;

    @Column(name = "estimated_distance", nullable = false)
    private Double estimatedDistance;

    @Column(name = "estimated_duration", nullable = false)
    private Long estimatedDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private DeliveryRouteStatus status;

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    private DeliveryRoute(Integer sequence, Long departureHubId, Long arrivalHubId,
                          Double estimatedDistance, Long estimatedDuration, DeliveryRouteStatus status) {
        this.sequence = sequence;
        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;
        this.estimatedDistance = estimatedDistance;
        this.estimatedDuration = estimatedDuration;
        this.status = status;
    }

    public static DeliveryRoute create(Integer sequence, Long departureHubId, Long arrivalHubId,
                                       Double estimatedDistance, Long estimatedDuration, DeliveryRouteStatus status) {
        return new DeliveryRoute(sequence, departureHubId, arrivalHubId, estimatedDistance, estimatedDuration, status);
    }


    public static DeliveryRoute fromShortestPathResponse(ShortestPathResponse response, Delivery delivery) {
        DeliveryRoute deliveryRoute = DeliveryRoute.create(
                response.sequence(),
                response.departureHubId(),
                response.arrivalHubId(),
                response.estimatedDistance(),
                response.estimatedDuration(),
                DeliveryRouteStatus.PENDING_HUB
        );
        deliveryRoute.setDelivery(delivery); // Delivery와의 연관관계 설정
        return deliveryRoute;
    }
}
