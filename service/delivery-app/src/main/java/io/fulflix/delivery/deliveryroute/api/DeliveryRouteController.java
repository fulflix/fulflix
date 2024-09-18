package io.fulflix.delivery.deliveryroute.api;

import io.fulflix.delivery.deliveryroute.api.dto.DeliveryRouteResponse;
import io.fulflix.delivery.deliveryroute.api.dto.DeliveryRouteUpdate;
import io.fulflix.delivery.deliveryroute.application.DeliveryRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery-route")
@RequiredArgsConstructor
public class DeliveryRouteController {
    private final DeliveryRouteService deliveryRouteService;

    // 배송 경로 생성
    @PostMapping("/{deliveryId}")
    public ResponseEntity<List<DeliveryRouteResponse>> createDeliveryRoute(@PathVariable Long deliveryId) {
        List<DeliveryRouteResponse> dto = deliveryRouteService.createDeliveryRoute(deliveryId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // 배송 경로 단건 조회 (모든 로그인 사용자)
    @GetMapping("/{deliveryRouteId}")
    public ResponseEntity<DeliveryRouteResponse> getDeliveryRoute(@PathVariable Long deliveryRouteId) {
        DeliveryRouteResponse dto = deliveryRouteService.getDeliveryRoute(deliveryRouteId);
        return ResponseEntity.ok(dto);
    }

    // 배송 경로 전체 조회 (모든 로그인 사용자)
    @GetMapping
    public ResponseEntity<Page<DeliveryRouteResponse>> getAllDeliveryRoute(
            @PageableDefault(
                    sort = "createdAt", direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<DeliveryRouteResponse> dto = deliveryRouteService.getAllDeliveryRoute(pageable);
        return ResponseEntity.ok(dto);
    }

    // 배송 경로 상태 변경 (마스터 관리자, 해당 허브 관리자)
    @PutMapping
    public ResponseEntity<DeliveryRouteResponse> updateDeliveryRouteStatus(@RequestBody DeliveryRouteUpdate deliveryRouteUpdate) {
        DeliveryRouteResponse dto = deliveryRouteService.updateDeliveryRouteStatus(deliveryRouteUpdate);
        return ResponseEntity.ok(dto);
    }

    // 배송 경로 상태 변경 (마스터 관리자, 해당 허브 관리자)
    @DeleteMapping("/{deliveryRouteId}")
    public ResponseEntity<Void> deleteDeliveryRoute(@PathVariable Long deliveryRouteId) {
        deliveryRouteService.deleteDeliveryRoute(deliveryRouteId);
        return ResponseEntity.noContent().build();
    }



}
