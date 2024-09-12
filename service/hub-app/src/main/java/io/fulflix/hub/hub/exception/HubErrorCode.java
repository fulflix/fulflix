package io.fulflix.hub.hub.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HubErrorCode {
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 허브를 찾을 수 없습니다."),
    INVALID_HUB_DATA(HttpStatus.BAD_REQUEST, "허브 정보가 올바르지 않습니다.");

    private final HttpStatus status;
    private final String message;

    HubErrorCode(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
