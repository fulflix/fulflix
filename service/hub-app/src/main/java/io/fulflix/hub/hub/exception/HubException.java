package io.fulflix.hub.hub.exception;

import io.fulflix.core.web.exception.BusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HubException extends BusinessException {

    private final HubErrorCode errorCode;

    public HubException(HubErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        this.errorCode = code;
    }

    public HubException(HttpStatus status, String code, String message, HubErrorCode errorCode, Object... args) {
        super(status, code, message, args);
        this.errorCode = errorCode;
    }
}
