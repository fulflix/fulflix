package io.fulflix.infra.client.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryResponse {
    private Long id;
    private Long orderId;
    private Long departureHubId;
    private Long arrivalHubId;
    private String deliveryAddress;
    private String recipient;
    private String recipientSlackId;
    private List<DeliveryRouteResponse> deliveryRouteResponseList;
}
