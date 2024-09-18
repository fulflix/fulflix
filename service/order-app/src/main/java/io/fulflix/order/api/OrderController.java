package io.fulflix.order.api;

import io.fulflix.core.app.context.annotation.CurrentUser;
import io.fulflix.core.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.*;
import io.fulflix.order.application.OrderFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        path = "/order",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
public class OrderController {
    private final OrderFacade orderFacade;

    // 주문 생성 (마스터 관리자)
    @PostMapping("/admin")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @Valid @RequestBody AdminCreateOrderRequest createOrderRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        CreateOrderResponse createOrderResponse = orderFacade.createAdminOrder(createOrderRequest, currentUser, role);
        return ResponseEntity.ok(createOrderResponse);
    }

    // 주문 생성 (생산 업체)
    @PostMapping("/supplier-company")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @Valid @RequestBody SupplierCreateOrderRequest createOrderRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        CreateOrderResponse createOrderResponse = orderFacade.createSupplierOrder(createOrderRequest, currentUser, role);
        return ResponseEntity.ok(createOrderResponse);
    }

    // 주문 생성 (수령 업체)
    @PostMapping("/receiver-company")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @Valid @RequestBody ReceiverCreateOrderRequest createOrderRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        CreateOrderResponse createOrderResponse = orderFacade.createReceiverOrder(createOrderRequest, currentUser, role);
        return ResponseEntity.ok(createOrderResponse);
    }

    // 주문 전체 조회 및 검색 (마스터 관리자, 허브 관리자, 생산 업체, 수령 업체)
    @GetMapping()
    public ResponseEntity<Page<OrderDetailResponse>> getAllOrders(
            @RequestParam(required = false, defaultValue = "0") Integer orderQuantity,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        Page<OrderDetailResponse> orders = orderFacade.getAllOrders(orderQuantity, pageable, currentUser, role);
        return ResponseEntity.ok(orders);
    }

    // 주문 단일 조회 (마스터 관리자, 허브 관리자, 생산 업체, 수령 업체)
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrder(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        OrderDetailResponse order = orderFacade.getOrder(id, currentUser, role);
        return ResponseEntity.ok(order);
    }

    // 주문 취소 (마스터 관리자, 허브 관리자, 생산 업체, 수령 업체)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        orderFacade.cancelOrder(id, currentUser, role);
        return ResponseEntity.noContent().build();
    }

    // 주문 수정 (마스터 관리자, 생산 업체, 수령 업체)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(
            @PathVariable Long id,
            @RequestBody UpdateOrderRequest updateOrderRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        orderFacade.updateOrder(id, updateOrderRequest, currentUser, role);
        return ResponseEntity.noContent().build();
    }
}
