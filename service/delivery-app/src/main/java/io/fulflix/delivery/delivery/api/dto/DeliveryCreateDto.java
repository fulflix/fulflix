package io.fulflix.delivery.delivery.api.dto;

import io.fulflix.delivery.delivery.domain.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DeliveryCreateDto {
    private Long orderId;
    private DeliveryStatus status;
    private Long departureHubId;
    private Long arrivalHubId;
    private String deliveryAddress;
    private String recipient;
    private String recipientSlackId;
}
