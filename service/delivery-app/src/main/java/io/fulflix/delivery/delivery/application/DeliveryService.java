package io.fulflix.delivery.delivery.application;

import io.fulflix.delivery.delivery.api.dto.DeliveryCreateDto;
import io.fulflix.delivery.delivery.api.dto.DeliveryResponseDto;
import io.fulflix.delivery.delivery.domain.Delivery;
import io.fulflix.delivery.delivery.domain.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryResponseDto createDelivery(DeliveryCreateDto dto) {
        Delivery delivery = Delivery.create(
                dto.getOrderId(),
                dto.getStatus(),
                dto.getDepartureHubId(),
                dto.getArrivalHubId(),
                dto.getDeliveryAddress(),
                dto.getRecipient(),
                dto.getRecipientSlackId()
        );
        deliveryRepository.save(delivery);
        return DeliveryResponseDto.of(delivery);
    }




}
