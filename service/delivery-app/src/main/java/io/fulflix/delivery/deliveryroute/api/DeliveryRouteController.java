package io.fulflix.delivery.deliveryroute.api;

import io.fulflix.delivery.deliveryroute.api.dto.DeliveryRouteResponse;
import io.fulflix.delivery.deliveryroute.application.DeliveryRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/delivery-route")
@RequiredArgsConstructor
public class DeliveryRouteController {
    private final DeliveryRouteService deliveryRouteService;

    @PostMapping("/{deliveryId}")
    public ResponseEntity<List<DeliveryRouteResponse>> createDeliveryRoute(@PathVariable Long deliveryId) {
        List<DeliveryRouteResponse> dto = deliveryRouteService.createDeliveryRoute(deliveryId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
