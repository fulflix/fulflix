package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyFacade {

    private final List<CompanyRetrieveStrategy> companyRetrieveStrategies;
    // HubAdminCompanyRetrieveStrategy - 허브 관리자
    // HubCompanyRetrieveStrategy - 허브 업체

    public Page<CompanyResponse> getAllCompaniesForHub(
        String query,
        Pageable pageable,
        Long currentUser,
        Role role
    ) {
        if (pageable.getPageSize() != 10 && pageable.getPageSize() != 30 && pageable.getPageSize() != 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        }

        return companyRetrieveStrategies.stream()
            .filter(it -> it.isMatched(role))
            .findAny()
            .orElseThrow()
            .retrieveCompanies(query, pageable, currentUser, role);
    }

}
