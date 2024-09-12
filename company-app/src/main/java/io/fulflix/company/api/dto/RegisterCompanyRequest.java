package io.fulflix.company.api.dto;

import io.fulflix.company.domain.Company;
import io.fulflix.company.domain.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCompanyRequest {
    @NotNull(message = "User Id를 입력해 주세요.")
    private Long userId;

    @NotNull(message = "Hub Id를 입력해 주세요.")
    private Long hubId;

    @NotBlank(message = "업체명을 입력해 주세요.")
    private String companyName;

    @NotNull(message = "업체 타입을 입력해 주세요.")
    private CompanyType companyType;

    @NotBlank(message = "업체 주소를 입력해 주세요.")
    private String companyAddress;

    public static Company toEntity(RegisterCompanyRequest registerCompanyRequest) {
        return Company.builder()
                .userId(registerCompanyRequest.userId)
                .hubId(registerCompanyRequest.hubId)
                .companyName(registerCompanyRequest.companyName)
                .companyType(registerCompanyRequest.companyType)
                .companyAddress(registerCompanyRequest.companyAddress)
                .build();
    }
}