package io.fulflix.delivery.deliveryroute.domain;

import lombok.Getter;

@Getter
public enum DeliveryRouteStatus {
    PENDING_HUB("출발 대기 중"),
    IN_TRANSIT("운송 중"),
    ARRIVED("도착 완료"),
    DELIVERING("배송 중");

    private final String description;

    DeliveryRouteStatus(String description) {
        this.description = description;
    }
}
