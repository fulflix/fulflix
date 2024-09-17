package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;

// 권한 별 업체 단일 조회 전략 (허브 관리자, 허브 업체)
public interface CompanyRetrieveByIdStrategy {
    CompanyResponse retrieveCompanyById(Long id, Long currentUser, Role role);

    boolean isMatched(Role role);
}
