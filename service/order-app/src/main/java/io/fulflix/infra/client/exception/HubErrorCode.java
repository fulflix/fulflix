package io.fulflix.infra.client.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HubErrorCode {
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 허브를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    HubErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}