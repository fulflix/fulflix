package io.fulflix.order.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.order.api.dto.AdminCreateOrderRequest;
import io.fulflix.order.api.dto.CreateOrderResponse;
import io.fulflix.order.api.dto.ReceiverCreateOrderRequest;
import io.fulflix.order.api.dto.SupplierCreateOrderRequest;
import io.fulflix.order.application.strategy.MasterAdminCreateOrder;
import io.fulflix.order.application.strategy.ReceiverCompanyCreateOrder;
import io.fulflix.order.application.strategy.SupplierCompanyCreateOrder;
import io.fulflix.order.exception.OrderErrorCode;
import io.fulflix.order.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final MasterAdminCreateOrder masterAdminCreateOrder;
    private final SupplierCompanyCreateOrder supplierCompanyCreateOrder;
    private final ReceiverCompanyCreateOrder receiverCompanyCreateOrder;

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
}
