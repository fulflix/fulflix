package io.fulflix.company.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompanyRequest {
    private Long hubId;
    private String companyName;
    private String companyAddress;
}