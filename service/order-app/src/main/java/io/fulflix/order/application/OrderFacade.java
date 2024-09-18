package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.CreateOrderRequest;
import io.fulflix.order.api.dto.CreateOrderResponse;
import io.fulflix.order.exception.OrderErrorCode;
import io.fulflix.order.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final List<OrderCreateStrategy> orderCreateStrategies;

    // 상품 등록
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        return orderCreateStrategies.stream()
                .filter(strategy -> strategy.isMatched(role))
                .findAny()
                .orElseThrow(() -> new OrderException(OrderErrorCode.UNAUTHORIZED_ACCESS))
                .createOrder(createOrderRequest, currentUser, role);
    }
}
