package io.fulflix.infra.client.delivery;

import io.fulflix.core.app.feign.FeignClientErrorDecoder;
import io.fulflix.core.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = DeliveryClient.DELIVERY_APP_CLIENT, configuration = {
        FulflixPrincipalRequestHeaderInterceptor.class,
        FeignClientErrorDecoder.class
})
public interface DeliveryClient extends DeliveryService {
    String DELIVERY_APP_CLIENT = "delivery-app";
    String CREATE_DELIVERY_URI = "/delivery";
    String CREATE_DELIVERY_ROUTE_URI = "/delivery-route/{deliveryId}";

    // 배송 생성 API
    @PostMapping(
            path = CREATE_DELIVERY_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    DeliveryResponse createDelivery(@RequestBody DeliveryRequest deliveryRequest);

    // 배송 경로 생성 API
    @PostMapping(
            path = CREATE_DELIVERY_ROUTE_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    void createDeliveryRoute(@PathVariable Long deliveryId);
}
