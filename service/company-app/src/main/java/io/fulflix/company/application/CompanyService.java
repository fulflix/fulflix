package io.fulflix.company.application;

import feign.FeignException;
import io.fulflix.core.web.principal.Role;
import io.fulflix.company.api.dto.CompanyDetailResponse;
import io.fulflix.company.api.dto.RegisterCompanyRequest;
import io.fulflix.company.domain.Company;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
import io.fulflix.infra.client.exception.HubErrorCode;
import io.fulflix.infra.client.exception.HubException;
import io.fulflix.infra.client.exception.UserErrorCode;
import io.fulflix.infra.client.exception.UserException;
import io.fulflix.infra.client.hub.HubClient;
import io.fulflix.infra.client.hub.HubResponse;
import io.fulflix.infra.client.user.UserClient;
import io.fulflix.infra.client.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final HubClient hubClient;
    private final UserClient userClient;

    // 업체 등록 (마스터 관리자, 허브 관리자)
    @Transactional
    public void registerCompany(RegisterCompanyRequest registerCompanyRequest, Role role) {
        validateCompanyRegisterProcess(registerCompanyRequest, role);

        Company company = RegisterCompanyRequest.toEntity(registerCompanyRequest);
        company.assignOwnerId(registerCompanyRequest.getOwnerId());
        companyRepo.save(company);
    }

    private void validateCompanyRegisterProcess(RegisterCompanyRequest registerCompanyRequest, Role role) {
        validateAdminAuthority(role);

        checkHubExists(registerCompanyRequest.getHubId());
        checkUserExists(registerCompanyRequest.getOwnerId());
        checkCompanyDuplication(registerCompanyRequest.getCompanyName());
    }

    // 업체 전체 조회 및 검색 (마스터 관리자)
    public Page<CompanyDetailResponse> getAllCompaniesForAdmin(String query, Pageable pageable, Role role) {
        validateMasterAdminAuthority(role);

        // TODO CustomPageableHandlerMethodArgumentResolver를 이용한 기본 페이지 사이즈 요청 값 고정 처리
        if (pageable.getPageSize() != 10 && pageable.getPageSize() != 30 && pageable.getPageSize() != 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        }

        if (hasSearchQuery(query)) {
            return companyRepo.findByCompanyNameContaining(query, pageable)
                .map(CompanyDetailResponse::fromEntity);
        }
        return companyRepo.findAll(pageable)
            .map(CompanyDetailResponse::fromEntity);
    }

    private static boolean hasSearchQuery(String query) {
        return query != null && !query.isEmpty();
    }

    // 업체 단일 조회 (마스터 관리자)
    public CompanyDetailResponse getCompanyByIdForAdmin(Long id, Role role) {
        validateMasterAdminAuthority(role);

        Company company = findCompanyById(id);
        return CompanyDetailResponse.fromEntity(company);
    }

    // hubId 존재 확인 예외처리 (FeignClient)
    private void checkHubExists(Long hubId) {
        try {
            HubResponse hubResponse = hubClient.getHubById(hubId);
        } catch (FeignException.NotFound e) {
            throw new HubException(HubErrorCode.HUB_NOT_FOUND);
        }
    }

    // userId 존재 확인 예외처리 (FeignClient)
    private void checkUserExists(Long ownerId) {
        try {
            UserResponse userResponse = userClient.getUserById(ownerId);
        } catch (FeignException.NotFound e) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
    }

    // 마스터 관리자 권한 확인 예외처리
    private void validateMasterAdminAuthority(Role role) {
        if (!isMasterAdmin(role)) {
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    // 마스터 관리자 & 허브 관리자 확인 예외처리
    private void validateAdminAuthority(Role role) {
        if (!isAdmin(role)) {
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    // 마스터 관리자 권한 확인
    private boolean isMasterAdmin(Role role) {
        return role.isMasterAdmin();
    }

    // 마스터 관리자 & 허브 관리자 권한 확인
    private boolean isAdmin(Role role) {
        return role.isMasterAdmin() || role.isHubAdmin();
    }

    // 업체명 중복 확인
    private void checkCompanyDuplication(String companyName) {
        if (companyRepo.existsByCompanyName(companyName)) {
            throw new CompanyException(CompanyErrorCode.DUPLICATE_COMPANY_NAME);
        }
    }

    // 삭제 여부와 상관없이 모든 업체 존재 확인 (관리자용)
    private Company findCompanyById(Long id) {
        return companyRepo.findById(id)
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }

}
