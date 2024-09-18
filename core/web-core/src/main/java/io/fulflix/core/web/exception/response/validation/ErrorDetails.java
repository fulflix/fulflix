package io.fulflix.core.web.exception.response.validation;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.FieldError;

public record ErrorDetails(
    List<ErrorDetail> elements
) {

    public static ErrorDetails from(List<FieldError> fieldErrors) {
        return new ErrorDetails(
            fieldErrors.stream()
                .map(fieldError -> ErrorDetail.of(
                    fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue()
                ))
                .collect(Collectors.toList())
        );
    }

}
