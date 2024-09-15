package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.UpdateCompanyRequest;

public interface CompanyUpdateStrategy {
    CompanyResponse updateCompany(
            Long id,
            UpdateCompanyRequest updateCompanyRequest,
            Long currentUser,
            Role role);

    boolean isMatched(Role role);
}
