package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 권한 별 업체 전체 조회 및 검색 전략 (허브 관리자, 허브 업체)
public interface CompanyRetrieveStrategy {
    Page<CompanyResponse> retrieveCompanies(
        String query,
        Pageable pageable,
        Long currentUser,
        Role role
    );

    boolean isMatched(Role role); // 권한이 일치하는지

    boolean hasQuery(String query); // 쿼리가 있는지
}
