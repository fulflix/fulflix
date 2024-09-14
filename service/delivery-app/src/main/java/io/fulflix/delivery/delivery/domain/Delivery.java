package io.fulflix.delivery.delivery.domain;

import io.fulflix.common.app.jpa.audit.Auditable;
import io.fulflix.delivery.delivery.api.dto.DeliveryUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_delivery")
public class Delivery extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "status", nullable = false)
    private DeliveryStatus status;

    @Column(name = "departure_hub_id", nullable = false)
    private Long departureHubId;

    @Column(name = "arrival_hub_id", nullable = false)
    private Long arrivalHubId;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "recipient_slack_id")
    private String recipientSlackId;

    private Delivery(Long orderId, DeliveryStatus status, Long departureHubId, Long arrivalHubId,
                     String deliveryAddress, String recipient, String recipientSlackId) {
        this.orderId = orderId;
        this.status = status;
        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;
        this.deliveryAddress = deliveryAddress;
        this.recipient = recipient;
        this.recipientSlackId = recipientSlackId;
    }

    public static Delivery create(Long orderId, DeliveryStatus status, Long departureHubId, Long arrivalHubId,
                                  String deliveryAddress, String recipient, String recipientSlackId) {
        return new Delivery(orderId, status, departureHubId, arrivalHubId, deliveryAddress, recipient, recipientSlackId);
    }


    public void update(DeliveryUpdateDto dto) {
        if (dto.status() != null) {
            this.status = dto.status();
        }
        if (dto.departureHubId() != null) {
            this.departureHubId = dto.departureHubId();
        }
        if (dto.arrivalHubId() != null) {
            this.arrivalHubId = dto.arrivalHubId();
        }
        if (dto.deliveryAddress() != null) {
            this.deliveryAddress = dto.deliveryAddress();
        }
        if (dto.recipient() != null) {
            this.recipient = dto.recipient();
        }
        if (dto.recipientSlackId() != null) {
            this.recipientSlackId = dto.recipientSlackId();
        }
    }


}
