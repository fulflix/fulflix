package io.fulflix.company.domain;

import lombok.Getter;

@Getter
public enum CompanyType {
    SUPPLIER("공급 업체"),
    RECEIVER("수령 업체");

    private final String description;

    private CompanyType(String description) {
        this.description = description;
    }
}