package io.fulflix.company.application.strategy;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.application.CompanyDeleteStrategy;
import io.fulflix.company.domain.Company;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubAdminDeleteStrategy implements CompanyDeleteStrategy {
    private final CompanyRepo companyRepo;

    @Override
    public void deleteCompany(Long id, Long currentUser, Role role) {
        Company company = companyRepo.findByIdAndHubIdAndIsDeletedFalse(id, currentUser)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS));

        company.delete(); // isDeleted = true로 설정
        companyRepo.save(company);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubAdmin();
    }
}
