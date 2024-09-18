package io.fulflix.company.application.strategy;

import io.fulflix.core.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.UpdateCompanyRequest;
import io.fulflix.company.application.CompanyUpdateStrategy;
import io.fulflix.company.application.validator.CompanyValidator;
import io.fulflix.company.domain.Company;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubAdminUpdateStrategy implements CompanyUpdateStrategy {
    private final CompanyRepo companyRepo;
    private final CompanyValidator companyValidator;

    @Override
    public CompanyResponse updateCompany(Long id, UpdateCompanyRequest updateCompanyRequest, Long currentUser, Role role) {
        Company company = companyRepo.findByIdAndHubIdAndIsDeletedFalse(id, currentUser)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS));

        if (updateCompanyRequest.getCompanyName() != null && !updateCompanyRequest.getCompanyName().equals(company.getCompanyName())) {
            companyValidator.checkCompanyDuplication(updateCompanyRequest.getCompanyName());
            company.updateCompanyName(updateCompanyRequest);
        }

        if (updateCompanyRequest.getCompanyAddress() != null && !updateCompanyRequest.getCompanyAddress().equals(company.getCompanyAddress())) {
            company.updateCompanyAddress(updateCompanyRequest);
        }

        return CompanyResponse.fromEntity(companyRepo.save(company));
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubAdmin();
    }
}
