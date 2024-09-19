package io.fulflix.delivery.delivery.api;

import io.fulflix.delivery.delivery.api.dto.DeliveryCreateDto;
import io.fulflix.delivery.delivery.api.dto.DeliveryResponseDto;
import io.fulflix.delivery.delivery.api.dto.DeliveryUpdateDto;
import io.fulflix.delivery.delivery.application.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // 배송 단건 조회
    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseDto> getDelivery(@PathVariable Long deliveryId) {
        DeliveryResponseDto response = deliveryService.getDelivery(deliveryId);
        return ResponseEntity.ok(response);
    }

    // 배송 전체 조회
    @GetMapping
    public ResponseEntity<Page<DeliveryResponseDto>> getAllDelivery(
            @PageableDefault(
                    sort = "createdAt", direction = Sort.Direction.ASC
            ) Pageable pageable
            ) {
        Page<DeliveryResponseDto> page = deliveryService.getAllDelivery(pageable);
        return ResponseEntity.ok(page);
    }

    // 배송 주소로 검색
    @GetMapping("/search")
    public ResponseEntity<Page<DeliveryResponseDto>> searchDelivery(
            @PageableDefault(
                    sort = "createdAt", direction = Sort.Direction.ASC
            ) Pageable pageable, @RequestParam String keyword
    ) {
        Page<DeliveryResponseDto> page = deliveryService.searchDelivery(keyword,pageable);

        return ResponseEntity.ok(page);
    }

    // 배송 수정
    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseDto> updateDelivery(@PathVariable Long deliveryId, @RequestBody DeliveryUpdateDto dto) {
        DeliveryResponseDto response = deliveryService.updateDelivery(deliveryId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 배송 삭제
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }
}
