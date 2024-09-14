package io.fulflix.company.application;

import feign.FeignException;
import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyDetailResponse;
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

    // 업체 전체 조회 및 검색 (마스터 관리자)
    public Page<CompanyDetailResponse> getAllCompaniesForAdmin(String query, int page, int size, String sortBy, String sortDirection, Long currentUser, Role role) {
        validateMasterAdminAuthority(role);

        if (size != 10 && size != 30 && size != 50) size = 10;
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // 마스터 관리자 : 삭제된 업체까지 모두 조회
        Page<Company> companies;

        if (query != null && !query.isEmpty())
            companies = companyRepo.findByCompanyNameContaining(query, sortedPageable); // 검색어 있으면
        else companies = companyRepo.findAll(sortedPageable); // 검색어 없으면

        return companies.map(CompanyDetailResponse::fromEntity);
    }

    // 업체 전체 조회 및 검색 (허브 관리자, 허브 업체)
    public Page<CompanyResponse> getAllCompaniesForHub(String query, int page, int size, String sortBy, String sortDirection, Long currentUser, Role role) {
        if (size != 10 && size != 30 && size != 50) size = 10;
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // 허브 관리자, 허브 업체 : 삭제되지 않은 업체 + 소속 허브의 업체만 조회
        Page<Company> companies;

        if (isHubAdmin(role)) {
            if (query != null && !query.isEmpty())
                companies = companyRepo.findByHubIdAndCompanyNameContainingAndIsDeletedFalse(currentUser, query, sortedPageable);
            else companies = companyRepo.findByHubIdAndIsDeletedFalse(currentUser, sortedPageable);
        } else if (isHubCompany(role)) {
            if (query != null && !query.isEmpty())
                companies = companyRepo.findByOwnerIdAndCompanyNameContainingAndIsDeletedFalse(currentUser, query, sortedPageable);
            else companies = companyRepo.findByOwnerIdAndIsDeletedFalse(currentUser, sortedPageable);
        } else throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);

        return companies.map(CompanyResponse::fromEntity);
    }

    // 업체 단일 조회 (마스터 관리자)
    public CompanyDetailResponse getCompanyByIdForAdmin(Long id, Long currentUser, Role role) {
        validateMasterAdminAuthority(role);

        Company company = findCompanyById(id);
        return CompanyDetailResponse.fromEntity(company);
    }

    // 업체 단일 조회 (허브 관리자, 허브 업체)
    public CompanyResponse getCompanyByIdForHub(Long id, Long currentUser, Role role) {
        Company company;

        if (isHubAdmin(role)) {
            company = companyRepo.findByIdAndHubIdAndIsDeletedFalse(id, currentUser)
                    .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
        } else if (isHubCompany(role)) {
            company = companyRepo.findByIdAndOwnerIdAndIsDeletedFalse(id, currentUser)
                    .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
        } else throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);

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

    // 허브 업체 권한 확인 예외처리
    private void validateHubCompanyAuthority(Role role) {
        if (!isHubCompany(role))
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 마스터 관리자 & 허브 관리자 확인 예외처리
    private void validateAdminAuthority(Role role) {
        if (!isAdmin(role))
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 마스터 관리자 & 허브 관리자 & 허브 업체 확인 예외처리
    private void validateAdminAndHubCompanyAuthority(Role role) {
        if (!isAdmin(role) && !isHubCompany(role))
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
}
