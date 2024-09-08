package io.fulflix.user.repo.model;

import lombok.Getter;

@Getter
public enum Role {
    MASTER_ADMIN("시스템 관리자"),
    HUB_ADMIN("허브 관리자"),
    HUB_COMPANY("허브 업체"),
    SUPPLY_COMPANY("공급 업체"),
    HUB_DELIVERY_MANAGER("허브 배송 담당자"),
    COMPANY_DELIVERY_MANAGER("업체 배송 담당자");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
