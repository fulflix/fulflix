package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.*;
import io.fulflix.order.application.strategy.MasterAdminCreateOrder;
import io.fulflix.order.application.strategy.ReceiverCompanyCreateOrder;
import io.fulflix.order.application.strategy.SupplierCompanyCreateOrder;
import io.fulflix.order.exception.OrderErrorCode;
import io.fulflix.order.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final MasterAdminCreateOrder masterAdminCreateOrder;
    private final SupplierCompanyCreateOrder supplierCompanyCreateOrder;
    private final ReceiverCompanyCreateOrder receiverCompanyCreateOrder;
    private final List<OrderRetrieveStrategy> orderRetrieveStrategies;
    private final List<OrderRetrieveByIdStrategy> orderRetrieveByIdStrategies;
    private final List<OrderCancelStrategy> orderCancelStrategies;
    private final List<OrderUpdateStrategy> orderUpdateStrategies;

    @Transactional
    public CreateOrderResponse createAdminOrder(AdminCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        if (role.isMasterAdmin()) {
            return masterAdminCreateOrder.AdminCreateOrder(createOrderRequest, currentUser, role);
        } else {
            throw new OrderException(OrderErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    @Transactional
    public CreateOrderResponse createSupplierOrder(SupplierCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        if (role.isHubCompany()) {
            return supplierCompanyCreateOrder.SupplierCreateOrder(createOrderRequest, currentUser, role);
        } else {
            throw new OrderException(OrderErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    @Transactional
    public CreateOrderResponse createReceiverOrder(ReceiverCreateOrderRequest createOrderRequest, Long currentUser, Role role) {
        if (role.isHubCompany()) {
            return receiverCompanyCreateOrder.ReceiverCreateOrder(createOrderRequest, currentUser, role);
        } else {
            throw new OrderException(OrderErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    // 주문 전체 조회 및 검색
    public Page<OrderDetailResponse> getAllOrders(
            Integer orderQuantity,
            Pageable pageable,
            Long currentUser,
            Role role
    ) {
        if (pageable.getPageSize() != 10 && pageable.getPageSize() != 30 && pageable.getPageSize() != 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        }

        return orderRetrieveStrategies.stream()
                .filter(it -> it.isMatched(role))
                .findAny()
                .orElseThrow()
                .retrieveOrders(orderQuantity, pageable, currentUser, role);
    }

    // 주문 단일 조회
    public OrderDetailResponse getOrder(Long id, Long currentUser, Role role) {
        return orderRetrieveByIdStrategies.stream()
                .filter(it -> it.isMatched(role))
                .findAny()
                .orElseThrow()
                .retrieveOrder(id, currentUser, role);
    }

    // 주문 취소
    public void cancelOrder(Long id, Long currentUser, Role role) {
        orderCancelStrategies.stream()
                .filter(strategy -> strategy.isMatched(role))
                .findAny()
                .orElseThrow(() -> new OrderException(OrderErrorCode.UNAUTHORIZED_ACCESS))
                .cancelOrder(id, currentUser, role);
    }

    // 주문 수정
    public void updateOrder(Long id, UpdateOrderRequest updateOrderRequest, Long currentUser, Role role) {
        orderUpdateStrategies.stream()
                .filter(strategy -> strategy.isMatched(role))
                .findAny()
                .orElseThrow(() -> new OrderException(OrderErrorCode.UNAUTHORIZED_ACCESS))
                .updateOrder(id, updateOrderRequest, currentUser, role);
    }
}
