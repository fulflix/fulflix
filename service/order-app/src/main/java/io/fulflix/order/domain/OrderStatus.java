package io.fulflix.order.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    SUCCESS("주문 성공"),
    FAIL("주문 실패");

    private final String description;

    private OrderStatus(String description) {
        this.description = description;
    }
}