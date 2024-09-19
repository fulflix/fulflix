package io.fulflix.infra.client.delivery;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonDeserialize(builder = DeliveryRequest.DeliveryRequestBuilder.class)
public class DeliveryRequest {
    private Long orderId;
    private Long departureHubId;
    private Long arrivalHubId;
    private String deliveryAddress;
    private String recipient;
    private String recipientSlackId;

//    @JsonPOJOBuilder(withPrefix = "")
//    public static class DeliveryRequestBuilder {
//    }

    @Override
    public String toString() {
        return "DeliveryRequest{" +
                "orderId=" + orderId +
                ", departureHubId=" + departureHubId +
                ", arrivalHubId=" + arrivalHubId +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", recipient='" + recipient + '\'' +
                ", recipientSlackId='" + recipientSlackId + '\'' +
                '}';
    }
}
