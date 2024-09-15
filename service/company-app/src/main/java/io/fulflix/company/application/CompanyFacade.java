package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.UpdateCompanyRequest;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
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
    private final List<CompanyRetrieveByIdStrategy> companyRetrieveByIdStrategies;
    private final List<CompanyUpdateStrategy> companyUpdateStrategies;
    // HubAdminCompanyRetrieveStrategy - 허브 관리자
    // HubCompanyRetrieveStrategy - 허브 업체

    // 전체 조회 및 검색
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

    // 단일 조회
    public CompanyResponse getCompanyByIdForHub(Long id, Long currentUser, Role role) {
        return companyRetrieveByIdStrategies.stream()
                .filter(it -> it.isMatched(role))
                .findAny()
                .orElseThrow()
                .retrieveCompanyById(id, currentUser, role);
    }

    // 수정
    public CompanyResponse updateCompany(Long id, UpdateCompanyRequest updateCompanyRequest, Long currentUser, Role role) {
        return companyUpdateStrategies.stream()
                .filter(strategy -> strategy.isMatched(role))
                .findAny()
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS))
                .updateCompany(id, updateCompanyRequest, currentUser, role);
    }
}
