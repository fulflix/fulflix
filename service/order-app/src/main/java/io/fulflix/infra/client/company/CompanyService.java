package io.fulflix.infra.client.company;

public interface CompanyService {
    CompanyDetailResponse getCompanyForAdminById(Long id);
    CompanyResponse getCompanyForCompanyById(Long id);
}
