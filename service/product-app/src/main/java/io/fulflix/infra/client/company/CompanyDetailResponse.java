package io.fulflix.infra.client.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CompanyDetailResponse {
    private Long id;
    private Long hubId;
    private String companyName;
    private String companyType;
    private String companyAddress;
    private boolean isDeleted;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
