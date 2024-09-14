package io.fulflix.delivery.delivery.application;

import io.fulflix.delivery.delivery.api.dto.DeliveryCreateDto;
import io.fulflix.delivery.delivery.api.dto.DeliveryResponseDto;
import io.fulflix.delivery.delivery.api.dto.DeliveryUpdateDto;
import io.fulflix.delivery.delivery.domain.Delivery;
import io.fulflix.delivery.delivery.domain.DeliveryRepository;
import io.fulflix.delivery.delivery.domain.DeliveryStatus;
import io.fulflix.delivery.delivery.exception.DeliveryErrorCode;
import io.fulflix.delivery.delivery.exception.DeliveryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    // 배송 생성
    @Transactional
    public DeliveryResponseDto createDelivery(DeliveryCreateDto dto) {
        Delivery delivery = Delivery.create(
                dto.orderId(),
                DeliveryStatus.PENDING_HUB,
                dto.departureHubId(),
                dto.arrivalHubId(),
                dto.deliveryAddress(),
                dto.recipient(),
                dto.recipientSlackId()
        );
        deliveryRepository.save(delivery);
        return DeliveryResponseDto.of(delivery);
    }

    // 배송 수정
    @Transactional
    public DeliveryResponseDto updateDelivery(Long id, DeliveryUpdateDto dto) {
        Delivery delivery = findDeliveryById(id);
        delivery.update(dto);
        return DeliveryResponseDto.of(delivery);
    }







    // 아이디로 배송 조회
    private Delivery findDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryException(DeliveryErrorCode.DELIVERY_NOT_FOUND));
    }


}
