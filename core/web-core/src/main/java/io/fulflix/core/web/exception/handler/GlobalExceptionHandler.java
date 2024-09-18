package io.fulflix.core.web.exception.handler;

import io.fulflix.core.web.exception.BusinessException;
import io.fulflix.core.web.exception.event.ThrowsExceptionEvent;
import io.fulflix.core.web.exception.response.GlobalErrorCode;
import io.fulflix.core.web.exception.response.GlobalErrorResponse;
import io.fulflix.core.web.exception.response.MethodArgumentNotValidErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApplicationEventPublisher eventPublisher;

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<GlobalErrorResponse> unexpectedException(
        Exception exception,
        HttpServletRequest request
    ) {
        GlobalErrorCode unexpectedError = GlobalErrorCode.UNEXPECTED_ERROR;
        GlobalErrorResponse errorResponse = GlobalErrorResponse.of(request, unexpectedError);

        return toErrorResponse(exception, unexpectedError.status, errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GlobalErrorResponse> businessException(
        BusinessException exception,
        HttpServletRequest request
    ) {
        GlobalErrorResponse errorResponse = GlobalErrorResponse.businessErrorOf(request, exception);
        return toErrorResponse(exception, exception.getStatus(), errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorResponse> methodArgumentNotValidException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        GlobalErrorCode methodArgumentNotValidError = GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID;
        MethodArgumentNotValidErrorResponse errorResponse = MethodArgumentNotValidErrorResponse.of(
            request,
            methodArgumentNotValidError,
            exception.getFieldErrors()
        );

        return toErrorResponse(exception, methodArgumentNotValidError.status, errorResponse);
    }

    @ExceptionHandler({
        MissingPathVariableException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class,
        HttpMediaTypeNotAcceptableException.class,
        NoResourceFoundException.class,
        HttpRequestMethodNotSupportedException.class,
        HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<GlobalErrorResponse> invalidRequestException(
        Exception exception,
        HttpServletRequest request
    ) {
        GlobalErrorCode errorCode = GlobalErrorCode.valueOf(exception);
        GlobalErrorResponse errorResponse = GlobalErrorResponse.of(request, errorCode);

        return toErrorResponse(exception, errorCode.status, errorResponse);
    }

    private ResponseEntity<GlobalErrorResponse> toErrorResponse(
        Exception exception,
        HttpStatus status,
        GlobalErrorResponse errorResponse
    ) {
        eventPublisher.publishEvent(ThrowsExceptionEvent.of(exception, errorResponse));
        return ResponseEntity.status(status).body(errorResponse);
    }

}
