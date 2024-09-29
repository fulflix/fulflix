package io.fulflix.order.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("주문 처리 중"),
    SUCCESS("주문 성공"),
    FAIL("주문 실패");

    private final String description;

    private OrderStatus(String description) {
        this.description = description;
    }
}