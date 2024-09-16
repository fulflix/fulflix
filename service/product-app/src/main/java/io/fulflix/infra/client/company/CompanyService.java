package io.fulflix.infra.client.company;

public interface CompanyService {
    CompanyDetailResponse getCompanyByIdForAdmin(Long id);
    CompanyResponse getCompanyByIdForHub(Long id);
}
