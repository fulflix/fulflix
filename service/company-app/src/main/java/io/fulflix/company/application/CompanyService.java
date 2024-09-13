package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.RegisterCompanyRequest;
import io.fulflix.company.api.dto.UpdateCompanyRequest;
import io.fulflix.company.domain.Company;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
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

    // 업체 등록 (마스터 관리자, 허브 관리자)
    public void registerCompany(RegisterCompanyRequest registerCompanyRequest, Long currentUser, Role role) {

        if (!isAdmin(role))
            throw new CompanyException(CompanyErrorCode.UNAUTHORIZED_ACCESS);

        checkCompanyDuplication(registerCompanyRequest.getCompanyName());

        Company company = RegisterCompanyRequest.toEntity(registerCompanyRequest);

        company.assignOwnerId(registerCompanyRequest.getOwnerId());
        company.applyCompanyCreated(currentUser);

        companyRepo.save(company);
    }

    // 업체 전체 조회 및 검색 (마스터 관리자, 허브 관리자)
    public Page<CompanyResponse> getAllCompanies(String query, int page, int size, String sortBy, String sortDirection) {
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
    public CompanyResponse getCompanyById(Long id) {
        Company company = findCompanyById(id);
        return CompanyResponse.fromEntity(company);
    }

    // 업체 수정 (마스터 관리자, 허브 관리자, 허브 업체)
    public CompanyResponse updateCompany(Long id, UpdateCompanyRequest updateCompanyRequest) {
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
    public void deleteCompany(Long id) {
        Company company = findCompanyById(id);
        company.delete(); // isDeleted = true
        companyRepo.save(company);
    }

    // 관리자 권한 확인
    private boolean isAdmin(Role role) {
        return role.equals(Role.MASTER_ADMIN) || role.equals(Role.HUB_ADMIN);
    }

    // 허브 업체 권한 확인
    private boolean isHubCompany(Role role) {
        return role.equals(Role.HUB_COMPANY);
    }

    // 삭제 여부와 상관없이 업체 존재 확인
    private Company findCompanyById(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }

    // TODO 삭제되지 않은 업체 존재 확인

    // 업체명 중복 확인
    private void checkCompanyDuplication(String companyName) {
        if (companyRepo.findByCompanyName(companyName).isPresent())
            throw new CompanyException(CompanyErrorCode.DUPLICATE_COMPANY_NAME);
    }

    // TODO 사용자 존재 확인

    // TODO 허브 존재 확인
}
