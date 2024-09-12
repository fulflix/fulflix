package io.fulflix.auth.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {
    BAD_CREDENTIALS(UNAUTHORIZED, "사용자 정보가 잘못 되었습니다.");

    private final HttpStatus status;
    private final String message;

    AuthErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
