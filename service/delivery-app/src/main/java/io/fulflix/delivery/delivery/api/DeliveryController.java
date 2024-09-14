package io.fulflix.delivery.delivery.api;

import io.fulflix.delivery.delivery.api.dto.DeliveryCreateDto;
import io.fulflix.delivery.delivery.api.dto.DeliveryResponseDto;
import io.fulflix.delivery.delivery.api.dto.DeliveryUpdateDto;
import io.fulflix.delivery.delivery.application.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/delivery")
@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    // 배송 생성
    @PostMapping
    public ResponseEntity<DeliveryResponseDto> createDelivery(@RequestBody DeliveryCreateDto dto) {
        DeliveryResponseDto response = deliveryService.createDelivery(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // 배송 수정
    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseDto> updateDelivery(@PathVariable Long deliveryId, @RequestBody DeliveryUpdateDto dto) {
        DeliveryResponseDto response = deliveryService.updateDelivery(deliveryId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
