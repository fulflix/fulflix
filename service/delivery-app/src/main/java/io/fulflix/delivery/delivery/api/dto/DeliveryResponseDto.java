package io.fulflix.delivery.delivery.api.dto;

import io.fulflix.delivery.delivery.domain.Delivery;
import io.fulflix.delivery.delivery.domain.DeliveryStatus;
import io.fulflix.delivery.deliveryroute.api.dto.DeliveryRouteResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DeliveryResponseDto {

    private Long id;
    private Long orderId;
    private DeliveryStatus status;
    private Long departureHubId;
    private Long arrivalHubId;
    private String deliveryAddress;
    private String recipient;
    private String recipientSlackId;
    private List<DeliveryRouteResponse> deliveryRoutes;

    public static DeliveryResponseDto of(Delivery delivery) {
        return DeliveryResponseDto.builder()
                .id(delivery.getId())
                .orderId(delivery.getOrderId())
                .status(delivery.getStatus())
                .departureHubId(delivery.getDepartureHubId())
                .arrivalHubId(delivery.getArrivalHubId())
                .deliveryAddress(delivery.getDeliveryAddress())
                .recipient(delivery.getRecipient())
                .recipientSlackId(delivery.getRecipientSlackId())
                .deliveryRoutes(delivery.getDeliveryRouteList().stream()
                        .map(DeliveryRouteResponse::fromEntity)
                        .toList())
                .build();
    }
}
