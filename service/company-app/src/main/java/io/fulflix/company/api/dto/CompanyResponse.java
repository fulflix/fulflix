package io.fulflix.company.api.dto;

import io.fulflix.company.domain.Company;
import io.fulflix.company.domain.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private Long hubId;
    private String companyName;
    private CompanyType companyType;
    private String companyAddress;

    public static CompanyResponse fromEntity(Company entity) {
        return CompanyResponse.builder()
                .id(entity.getId())
                .hubId(entity.getHubId())
                .companyName(entity.getCompanyName())
                .companyType(entity.getCompanyType())
                .companyAddress(entity.getCompanyAddress())
                .build();
    }
}
