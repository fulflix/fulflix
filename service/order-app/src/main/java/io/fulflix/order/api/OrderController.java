package io.fulflix.order.api;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.AdminCreateOrderRequest;
import io.fulflix.order.api.dto.CreateOrderResponse;
import io.fulflix.order.api.dto.ReceiverCreateOrderRequest;
import io.fulflix.order.api.dto.SupplierCreateOrderRequest;
import io.fulflix.order.application.OrderFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
