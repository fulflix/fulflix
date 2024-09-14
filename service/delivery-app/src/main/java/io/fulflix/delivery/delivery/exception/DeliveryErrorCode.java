package io.fulflix.delivery.delivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DeliveryErrorCode {
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배송을 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INVALID_DEPARTURE_HUB(HttpStatus.BAD_REQUEST, "출발 허브가 존재하지 않습니다."),
    INVALID_ARRIVAL_HUB(HttpStatus.BAD_REQUEST, "도착 허브가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    DeliveryErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}