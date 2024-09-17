package io.fulflix.product.exception;

import io.fulflix.common.web.exception.BusinessException;
import lombok.Getter;

@Getter
public class ProductException extends BusinessException {
    private final ProductErrorCode errorCode;

    public ProductException(ProductErrorCode code) {
        super(code.getStatus(), code.name(), code.getMessage());
        errorCode = code;
    }

    public ProductException(ProductErrorCode code, Object... args) {
        super(code.getStatus(), code.name(), code.getMessage(), args);
        this.errorCode = code;
    }
}