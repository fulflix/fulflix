package io.fulflix.infra.client.exception;

import io.fulflix.common.web.exception.BusinessException;
import lombok.Getter;

@Getter
public class HubException extends BusinessException {
    private final HubErrorCode errorCode;

    public HubException(HubErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        errorCode = code;
    }

    public HubException(HubErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), code.getMessage(), args);
        this.errorCode = code;
    }
}