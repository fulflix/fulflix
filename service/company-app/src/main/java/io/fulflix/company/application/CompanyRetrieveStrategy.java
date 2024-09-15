package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 권한별 업체 목록 조회 전략
public interface CompanyRetrieveStrategy {
    Page<CompanyResponse> retrieveCompanies(
        String query,
        Pageable sortedPageable,
        Long currentUser,
        Role role
    );

    boolean isMatched(Role role);

    boolean hasQuery(String query);
}
