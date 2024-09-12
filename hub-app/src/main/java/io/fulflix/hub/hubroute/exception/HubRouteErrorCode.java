package io.fulflix.hub.hubroute.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HubRouteErrorCode {
    HUB_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 허브 경로를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    HubRouteErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
