package io.fulflix.infra.client.exception;

import io.fulflix.core.web.exception.BusinessException;
import lombok.Getter;

@Getter
public class CompanyException extends BusinessException {
    private final CompanyErrorCode errorCode;

    public CompanyException(CompanyErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        errorCode = code;
    }

    public CompanyException(CompanyErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), code.getMessage(), args);
        this.errorCode = code;
    }
}
