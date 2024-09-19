package io.fulflix.company.api.dto;

import io.fulflix.company.domain.Company;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailResponse extends CompanyResponse {

    private Boolean isDeleted;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public static CompanyDetailResponse fromEntity(Company entity) {
        return CompanyDetailResponse.builder()
            .id(entity.getId())
            .hubId(entity.getHubId())
            .ownerId(entity.getOwnerId())
            .companyName(entity.getCompanyName())
            .companyType(entity.getCompanyType())
            .companyAddress(entity.getCompanyAddress())
            .isDeleted(entity.isDeleted())
            .updatedAt(entity.getUpdatedAt())
            .updatedBy(entity.getUpdatedBy())
            .build();
    }

}
