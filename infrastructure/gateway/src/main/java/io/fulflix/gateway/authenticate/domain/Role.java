package io.fulflix.gateway.authenticate.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Role {
    MASTER_ADMIN("시스템 관리자"),
    HUB_ADMIN("허브 관리자"),
    HUB_COMPANY("허브 업체"),
    HUB_DELIVERY_MANAGER("허브 배송 담당자"),
    COMPANY_DELIVERY_MANAGER("업체 배송 담당자");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public static String[] getAllRoles() {
        return Arrays.stream(values())
            .map(Enum::name)
            .toArray(String[]::new);
    }
}
