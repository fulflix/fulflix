package io.fulflix.infra.client.delivery;

public interface DeliveryService {
    DeliveryResponse createDelivery(DeliveryRequest deliveryRequest);
    void createDeliveryRoute(Long deliveryId);
}
