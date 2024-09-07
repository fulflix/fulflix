package io.fulflix.auth.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final AuthErrorCode code;

    public AuthException(AuthErrorCode code, String... args) {
        super(formattedMessage(code.getMessage(), args));
        this.code = code;
    }

    private static String formattedMessage(String message, String... args) {
        return message.formatted(args);
    }

}
