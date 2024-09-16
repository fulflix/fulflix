package io.fulflix.infra.client.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompanyResponse {
    private Long id;
    private Long hubId;
    private Long ownerId;
    private String companyName;
    private String companyType;
    private String companyAddress;
}