package io.fulflix.core.web.exception.response;

import io.fulflix.core.web.exception.response.validation.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public final class MethodArgumentNotValidErrorResponse extends GlobalErrorResponse {

    private final ErrorDetails errorDetails;

    private MethodArgumentNotValidErrorResponse(
        HttpServletRequest request,
        GlobalErrorCode errorCode,
        ErrorDetails errorDetails
    ) {
        super(
            request.getMethod(),
            request.getRequestURI(),
            errorCode.name(),
            errorCode.getMessage(),
            LocalDateTime.now()
        );
        this.errorDetails = errorDetails;
    }

    public static MethodArgumentNotValidErrorResponse of(
        HttpServletRequest request,
        GlobalErrorCode basicErrorCode,
        List<FieldError> fieldErrors
    ) {
        return new MethodArgumentNotValidErrorResponse(
            request,
            basicErrorCode,
            ErrorDetails.from(fieldErrors)
        );
    }

}
