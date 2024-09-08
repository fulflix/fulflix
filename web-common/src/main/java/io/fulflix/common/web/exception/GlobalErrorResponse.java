package io.fulflix.common.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public record GlobalErrorResponse(
    String method,
    String path,
    String code,
    String message,
    LocalDateTime timestamp
) {

    public static GlobalErrorResponse of(
        HttpServletRequest request,
        GlobalErrorCode errorCode
    ) {
        return new GlobalErrorResponse(
            request.getMethod(),
            request.getRequestURI(),
            errorCode.name(),
            errorCode.getMessage(),
            LocalDateTime.now()
        );
    }

    public static GlobalErrorResponse businessErrorOf(
        HttpServletRequest request,
        BusinessException exception
    ) {
        return new GlobalErrorResponse(
            request.getMethod(),
            request.getRequestURI(),
            exception.getCode(),
            exception.getMessage(),
            LocalDateTime.now()
        );
    }

}
