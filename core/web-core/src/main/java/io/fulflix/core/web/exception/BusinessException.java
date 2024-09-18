package io.fulflix.core.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    protected HttpStatus status;
    private final String code;
    private final String message;

    public BusinessException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public BusinessException(HttpStatus status, String code, String message, Object... args) {
        super(formattingErrorMessage(message, args));
        this.status = status;
        this.code = code;
        this.message = formattingErrorMessage(message, args);
    }

    private static String formattingErrorMessage(String message, Object... objects) {
        return message.formatted(objects);
    }

}
