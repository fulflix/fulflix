package io.fulflix.common.web.principal;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Role {
    MASTER_ADMIN("시스템 관리자"),
    HUB_ADMIN("허브 관리자"),
    HUB_COMPANY("허브 업체"),
    SUPPLY_COMPANY("공급 업체"),
    HUB_DELIVERY_MANAGER("허브 배송 담당자"),
    COMPANY_DELIVERY_MANAGER("업체 배송 담당자"),
    ANONYMOUS("익명 사용자");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public static Role valueOfRoleName(String roleName) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(roleName))
            .findFirst()
            .orElse(ANONYMOUS);
    }

    public boolean isMasterAdmin() {
        return this == MASTER_ADMIN;
    }

    public boolean isHubAdmin() {
        return this == HUB_ADMIN;
    }

    public boolean isHubCompany() {
        return this == HUB_COMPANY;
    }
}
