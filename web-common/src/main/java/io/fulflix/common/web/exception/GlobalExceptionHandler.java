package io.fulflix.common.web.exception;

import static io.fulflix.common.web.exception.GlobalErrorCode.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import io.fulflix.common.web.exception.event.ThrowsExceptionEvent;
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
        GlobalErrorResponse errorResponse = GlobalErrorResponse.of(request, UNEXPECTED_ERROR);
        return errorResponse(exception, INTERNAL_SERVER_ERROR, errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GlobalErrorResponse> businessException(
        BusinessException exception,
        HttpServletRequest request
    ) {
        GlobalErrorResponse errorResponse = GlobalErrorResponse.businessErrorOf(request, exception);
        return errorResponse(exception, exception.getStatus(), errorResponse);
    }

    @ExceptionHandler({
        MissingPathVariableException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        MethodArgumentNotValidException.class,
        HttpMessageNotReadableException.class,
        HttpMediaTypeNotAcceptableException.class,
        HttpRequestMethodNotSupportedException.class,
        HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<GlobalErrorResponse> invalidRequestException(
        Exception exception,
        HttpServletRequest request
    ) {
        GlobalErrorCode errorCode = GlobalErrorCode.valueOf(exception);
        GlobalErrorResponse errorResponse = GlobalErrorResponse.of(request, errorCode);

        return errorResponse(exception, errorCode.status, errorResponse);
    }

    private ResponseEntity<GlobalErrorResponse> errorResponse(
        Exception exception,
        HttpStatus status,
        GlobalErrorResponse errorResponse
    ) {
        eventPublisher.publishEvent(ThrowsExceptionEvent.of(exception, errorResponse));
        return ResponseEntity.status(status).body(errorResponse);
    }

}
