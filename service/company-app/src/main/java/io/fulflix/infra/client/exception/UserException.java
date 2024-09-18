package io.fulflix.infra.client.exception;

import io.fulflix.core.web.exception.BusinessException;
import lombok.Getter;

@Getter
public class UserException extends BusinessException {
    private final UserErrorCode errorCode;

    public UserException(UserErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        errorCode = code;
    }

    public UserException(UserErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), code.getMessage(), args);
        this.errorCode = code;
    }
}
