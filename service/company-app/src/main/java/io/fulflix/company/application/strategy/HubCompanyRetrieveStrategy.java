package io.fulflix.company.application.strategy;

import io.fulflix.core.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.application.CompanyRetrieveStrategy;
import io.fulflix.company.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubCompanyRetrieveStrategy implements CompanyRetrieveStrategy {

    private final CompanyRepo companyRepo;

    @Override
    public Page<CompanyResponse> retrieveCompanies(
            String query,
            Pageable pageable,
            Long currentUser,
            Role role
    ) {
        if (hasQuery(query)) {
            return companyRepo.findByOwnerIdAndCompanyNameContainingAndIsDeletedFalse(
                    currentUser,
                    query,
                    pageable
            ).map(CompanyResponse::fromEntity);
        }

        return companyRepo.findByOwnerIdAndIsDeletedFalse(currentUser, pageable)
                .map(CompanyResponse::fromEntity);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubCompany();
    }

    @Override
    public boolean hasQuery(String query) {
        return query != null && !query.isEmpty();
    }

}
