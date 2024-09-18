package io.fulflix.auth.exception;

import io.fulflix.core.web.exception.BusinessException;
import lombok.Getter;

@Getter
public class AuthException extends BusinessException {

    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        this.errorCode = code;
    }

    public AuthException(AuthErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), code.getMessage(), args);
        this.errorCode = code;
    }

}
