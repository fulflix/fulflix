package io.fulflix.company.application.strategy;

import io.fulflix.company.application.CompanyDeleteStrategy;
import io.fulflix.company.domain.Company;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
import io.fulflix.core.web.principal.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MasterAdminDeleteStrategy implements CompanyDeleteStrategy {

    private final CompanyRepo companyRepo;

    @Override
    @Transactional
    public void deleteCompany(Long id, Long currentUser, Role role) {
        Company company = companyRepo.findById(id)
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));

        company.delete();
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isMasterAdmin();
    }

}
