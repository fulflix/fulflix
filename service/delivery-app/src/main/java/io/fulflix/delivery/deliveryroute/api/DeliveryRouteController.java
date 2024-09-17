package io.fulflix.delivery.deliveryroute.api;

import io.fulflix.delivery.deliveryroute.application.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-route")
@RequiredArgsConstructor
public class DeliveryRouteController {
    private final DeliveryService deliveryService;
}
