package io.fulflix.company.application;

import feign.FeignException;
import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.RegisterCompanyRequest;
import io.fulflix.company.api.dto.UpdateCompanyRequest;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final HubClient hubClient;
    private final UserClient userClient;

    // 업체 등록 (마스터 관리자, 허브 관리자)
    public void registerCompany(RegisterCompanyRequest registerCompanyRequest, Long currentUser, Role role) {
        validateAdminAuthority(role);

        checkHubExists(registerCompanyRequest.getHubId());
        checkUserExists(registerCompanyRequest.getOwnerId());

        checkCompanyDuplication(registerCompanyRequest.getCompanyName());

        Company company = RegisterCompanyRequest.toEntity(registerCompanyRequest);

        company.assignOwnerId(registerCompanyRequest.getOwnerId());
        company.applyCompanyCreated(currentUser);

        companyRepo.save(company);
    }

    // 업체 전체 조회 및 검색 (마스터 관리자, 허브 관리자)
    public Page<CompanyResponse> getAllCompanies(String query, int page, int size, String sortBy, String sortDirection, Long currentUser, Role role) {
        validateAdminAuthority(role);

        Page<Company> companies;

        // 페이지 크기 제한
        if (size != 10 && size != 30 && size != 50)
            size = 10;

        // 정렬 방향 설정
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Pageable 객체 생성
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (query != null && !query.isEmpty())
            companies = companyRepo.findByCompanyNameContainingAndIsDeletedFalse(query, sortedPageable);
        else
            companies = companyRepo.findAllByIsDeletedFalse(sortedPageable);

        return companies.map(CompanyResponse::fromEntity);
    }

    // 업체 단일 조회 (마스터 관리자, 허브 관리자, 허브 업체)
    public CompanyResponse getCompanyById(Long id, Long currentUser, Role role) {
//        validateAdminAndHubCompanyAuthority(role);

        Company company = findCompanyById(id);
        return CompanyResponse.fromEntity(company);
    }

    // 업체 수정 (마스터 관리자, 허브 관리자, 허브 업체)
    public CompanyResponse updateCompany(Long id, UpdateCompanyRequest updateCompanyRequest, Long currentUser, Role role) {
//        validateAdminAndHubCompanyAuthority(role);

        Company company = findCompanyById(id);

        if (updateCompanyRequest.getHubId() != null && !updateCompanyRequest.getHubId().equals(company.getHubId()))
            company.updateHubId(updateCompanyRequest);

        if (updateCompanyRequest.getCompanyName() != null && !updateCompanyRequest.getCompanyName().equals(company.getCompanyName())) {
            checkCompanyDuplication(updateCompanyRequest.getCompanyName());
            company.updateCompanyName(updateCompanyRequest);
        }

        if (updateCompanyRequest.getCompanyAddress() != null && !updateCompanyRequest.getCompanyAddress().equals(company.getCompanyAddress()))
            company.updateCompanyAddress(updateCompanyRequest);

        return CompanyResponse.fromEntity(companyRepo.save(company));
    }

    // 업체 삭제 (마스터 관리자, 허브 관리자)
    public void deleteCompany(Long id, Long currentUser, Role role) {
        Company company;

        if (isMasterAdmin(role)) {
            company = findCompanyById(id);
        } else if (isHubAdmin(role)) {
            // 허브 관리자는 삭제되지 않은 업체만 조회 가능
            company = companyRepo.findByIdAndIsDeletedFalse(id)
                    .orElseThrow(() -> new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS));

            // 현재 로그인한 사용자가 허브 업체와 일치한지 확인
            if (!company.getHubId().equals(currentUser))
                throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);

        } else {
            // 마스터 관리자도 아니고 허브 관리자도 아닌 경우 예외 처리
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 업체 삭제 처리
        company.delete(); // isDeleted = true로 설정
        companyRepo.save(company);
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
        if (!isMasterAdmin(role))
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 허브 관리자 권한 확인 예외처리
    private void validateHubAdminAuthority(Role role) {
        if (!isHubAdmin(role))
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 마스터 관리자 & 허브 관리자 확인 예외처리
    private void validateAdminAuthority(Role role) {
        if (!isAdmin(role))
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 마스터 관리자 & 허브 관리자 & 허브 업체 확인 예외처리
    private void validateAdminAndHubCompanyAuthority(Role role, Company company, Long currentUser) {
        if (!isAdmin(role) && (!isHubCompany(role) && !company.getOwnerId().equals(currentUser)))
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 마스터 관리자 권한 확인
    private boolean isMasterAdmin(Role role) {
        return role.isMasterAdmin();
    }

    // 허브 관리자 권한 확인
    private boolean isHubAdmin(Role role) {
        return role.isHubAdmin();
    }

    // 마스터 관리자 & 허브 관리자 권한 확인
    private boolean isAdmin(Role role) {
        return role.isMasterAdmin() || role.isHubAdmin();
    }

    // 허브 업체 권한 확인
    private boolean isHubCompany(Role role) {
        return role.isHubCompany();
    }

    // 업체명 중복 확인
    private void checkCompanyDuplication(String companyName) {
        if (companyRepo.findByCompanyName(companyName).isPresent())
            throw new CompanyException(CompanyErrorCode.DUPLICATE_COMPANY_NAME);
    }

    // 삭제 여부와 상관없이 모든 업체 존재 확인 (관리자용)
    private Company findCompanyById(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }

    // is_deleted = false인 업체만 존재 확인 (허브 관리자 & 업체용)
    private Company findActiveCompanyById(Long id) {
        return companyRepo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }
}
