package io.fulflix.company.application.strategy;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.application.CompanyRetrieveByIdStrategy;
import io.fulflix.company.domain.Company;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubAdminRetrieveByIdStrategy implements CompanyRetrieveByIdStrategy {
    private final CompanyRepo companyRepo;

    @Override
    public CompanyResponse retrieveCompanyById(Long id, Long currentUser, Role role) {
        Company company = companyRepo.findByIdAndHubIdAndIsDeletedFalse(id, currentUser)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
        return CompanyResponse.fromEntity(company);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubAdmin();
    }
}
