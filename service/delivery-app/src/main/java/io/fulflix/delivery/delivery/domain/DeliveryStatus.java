package io.fulflix.delivery.delivery.domain;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    PENDING_HUB("허브 대기 중"),
    IN_TRANSIT("운송 중"),
    ARRIVED("목적지 도착"),
    DELIVERING("배송 중");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}