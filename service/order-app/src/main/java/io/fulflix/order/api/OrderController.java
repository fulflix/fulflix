package io.fulflix.order.api;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.CreateOrderRequest;
import io.fulflix.order.api.dto.CreateOrderResponse;
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

    // 주문 생성 (마스터 관리자, 허브 업체)
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest createOrderRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        CreateOrderResponse createOrderResponse = orderFacade.createOrder(createOrderRequest, currentUser, role);
        return ResponseEntity.ok(createOrderResponse);
    }
}
