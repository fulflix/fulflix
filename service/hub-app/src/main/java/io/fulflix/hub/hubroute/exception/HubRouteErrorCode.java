package io.fulflix.hub.hubroute.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HubRouteErrorCode {
    HUB_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 허브 경로를 찾을 수 없습니다."),
    SAME_DEPARTURE_AND_ARRIVAL(HttpStatus.BAD_REQUEST, "출발 허브와 도착 허브가 같습니다.");

    private final HttpStatus status;
    private final String message;

    HubRouteErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
