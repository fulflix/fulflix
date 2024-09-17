package io.fulflix.delivery.deliveryroute.application;

import io.fulflix.delivery.deliveryroute.domain.DeliveryRouteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRouteRepo deliveryRouteRepo;
}
