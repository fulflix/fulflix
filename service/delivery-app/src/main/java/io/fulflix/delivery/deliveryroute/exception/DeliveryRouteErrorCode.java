package io.fulflix.delivery.deliveryroute.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DeliveryRouteErrorCode {
    DELIVERY_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배송경로를 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    DeliveryRouteErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}