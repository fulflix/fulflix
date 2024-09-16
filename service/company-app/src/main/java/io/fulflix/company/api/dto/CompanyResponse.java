package io.fulflix.company.api.dto;

import io.fulflix.company.domain.Company;
import io.fulflix.company.domain.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private Long hubId;
    private Long ownerId;
    private String companyName;
    private CompanyType companyType;
    private String companyAddress;

    public static CompanyResponse fromEntity(Company entity) {
        return CompanyResponse.builder()
                .id(entity.getId())
                .hubId(entity.getHubId())
                .ownerId(entity.getOwnerId())
                .companyName(entity.getCompanyName())
                .companyType(entity.getCompanyType())
                .companyAddress(entity.getCompanyAddress())
                .build();
    }
}
