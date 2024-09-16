package io.fulflix.infra.client.company;

public interface CompanyService {
    CompanyDetailResponse getCompanyByIdForAdmin(Long id);
}
