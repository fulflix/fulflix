package io.fulflix.hub.hubroute.exception;

import io.fulflix.common.web.exception.BusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HubRouteException extends BusinessException {

    private final HubRouteErrorCode errorCode;

    public HubRouteException(HubRouteErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.name(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HubRouteException(HttpStatus status, String code, String message, HubRouteErrorCode errorCode, Object... args) {
        super(errorCode.getStatus(), errorCode.name(), errorCode.getMessage(), args);
        this.errorCode = errorCode;
    }
}
