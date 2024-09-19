package io.fulflix.core.web.exception.response;

import io.fulflix.core.web.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PROTECTED)
public sealed class GlobalErrorResponse permits MethodArgumentNotValidErrorResponse {

    private String method;
    private String path;
    private String code;
    private String message;
    private LocalDateTime timestamp;

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
