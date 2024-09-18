package io.fulflix.delivery.delivery.exception;

import io.fulflix.core.web.exception.BusinessException;
import lombok.Getter;

@Getter
public class DeliveryException extends BusinessException {
    private final DeliveryErrorCode errorCode;

    public DeliveryException(DeliveryErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        this.errorCode = code;
    }

    public DeliveryException(DeliveryErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), String.format(code.getMessage(), args));
        this.errorCode = code;
    }
}
