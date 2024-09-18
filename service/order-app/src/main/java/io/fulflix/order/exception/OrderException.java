package io.fulflix.order.exception;

import io.fulflix.common.web.exception.BusinessException;
import lombok.Getter;

@Getter
public class OrderException extends BusinessException {
    private final OrderErrorCode errorCode;

    public OrderException(OrderErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        errorCode = code;
    }

    public OrderException(OrderErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), code.getMessage(), args);
        this.errorCode = code;
    }
}