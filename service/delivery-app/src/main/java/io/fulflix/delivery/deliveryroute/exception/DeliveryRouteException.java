package io.fulflix.delivery.deliveryroute.exception;

import io.fulflix.common.web.exception.BusinessException;
import io.fulflix.delivery.delivery.exception.DeliveryErrorCode;
import io.fulflix.delivery.deliveryroute.domain.DeliveryRoute;
import lombok.Getter;

@Getter
public class DeliveryRouteException extends BusinessException {

    private final DeliveryRouteErrorCode errorCode;

    public DeliveryRouteException(DeliveryRouteErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        this.errorCode = code;
    }

    public DeliveryRouteException(DeliveryRouteErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), String.format(code.getMessage(), args));
        this.errorCode = code;
    }
}
