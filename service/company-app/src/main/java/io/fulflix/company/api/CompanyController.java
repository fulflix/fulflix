package io.fulflix.company.api;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyDetailResponse;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.RegisterCompanyRequest;
import io.fulflix.company.api.dto.UpdateCompanyRequest;
import io.fulflix.company.application.CompanyFacade;
import io.fulflix.company.application.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.fulflix.common.web.utils.ResponseEntityUtils.created;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        path = "/company",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyFacade companyFacade;

    // 업체 등록 (마스터 관리자, 허브 관리자)
    @PostMapping
    public ResponseEntity<Void> registerCompany(
            @Valid @RequestBody RegisterCompanyRequest registerCompanyRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        companyService.registerCompany(registerCompanyRequest, currentUser, role);
        return created("/company");
    }

    // 업체 전체 조회 및 검색 (마스터 관리자)
    @GetMapping("/admin")
    public ResponseEntity<Page<CompanyDetailResponse>> getAllCompaniesForAdmin(
            @RequestParam(required = false, defaultValue = "") String query, // 검색
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 페이징 및 정렬
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        Page<CompanyDetailResponse> companies = companyService.getAllCompaniesForAdmin(query, pageable, currentUser, role);
        return ResponseEntity.ok(companies);
    }

    // 업체 전체 조회 및 검색 (허브 관리자, 허브 업체)
    @GetMapping("/hub")
    public ResponseEntity<Page<CompanyResponse>> getAllCompaniesForHub(
            @RequestParam(required = false, defaultValue = "") String query, // 검색
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 페이징 및 정렬
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        Page<CompanyResponse> companies = companyFacade.getAllCompaniesForHub(query, pageable, currentUser, role);
        return ResponseEntity.ok(companies);
    }

    // 업체 단일 조회 (마스터 관리자)
    @GetMapping("/admin/{id}")
    public ResponseEntity<CompanyDetailResponse> getCompanyByIdForAdmin(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        CompanyDetailResponse company = companyService.getCompanyByIdForAdmin(id, currentUser, role);
        return ResponseEntity.ok(company);
    }

    // 업체 단일 조회 (허브 관리자, 허브 업체)
    @GetMapping("/hub/{id}")
    public ResponseEntity<CompanyResponse> getCompanyByIdForHub(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        CompanyResponse company = companyFacade.getCompanyByIdForHub(id, currentUser, role);
        return ResponseEntity.ok(company);
    }

    // 업체 수정 (마스터 관리자, 허브 관리자, 허브 업체)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCompanyRequest updateCompanyRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        companyFacade.updateCompany(id, updateCompanyRequest, currentUser, role);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }

    // 업체 삭제 (마스터 관리자, 허브 관리자)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        companyService.deleteCompany(id, currentUser, role);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}
