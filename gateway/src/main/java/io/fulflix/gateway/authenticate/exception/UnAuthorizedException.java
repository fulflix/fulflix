package io.fulflix.gateway.authenticate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnAuthorizedException extends RuntimeException {

    private final HttpStatus status;
    private final String code;
    private final String message;

    public UnAuthorizedException(AuthErrorCode errorCode, Object... args) {
        super(formattingErrorMessage(errorCode.getMessage(), args));
        status = errorCode.getStatus();
        code = errorCode.name();
        message = formattingErrorMessage(errorCode.getMessage(), args);
    }

    public static String formattingErrorMessage(String message, Object... objects) {
        return message.formatted(objects);
    }

}
