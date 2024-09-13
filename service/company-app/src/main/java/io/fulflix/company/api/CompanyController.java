package io.fulflix.company.api;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.RegisterCompanyRequest;
import io.fulflix.company.api.dto.UpdateCompanyRequest;
import io.fulflix.company.application.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // 업체 전체 조회 및 검색 (마스터 관리자, 허브 관리자)
    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false, defaultValue = "") String query, // 검색
            @RequestParam(defaultValue = "10") int size, // 페이지 크기
            @RequestParam(defaultValue = "createdAt") String sortBy, // 정렬 기준
            @RequestParam(defaultValue = "desc") String sortDirection, // 정렬 방향
            @RequestParam(defaultValue = "0") int page, // 페이지 번호
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        Page<CompanyResponse> companies = companyService.getAllCompanies(query, page, size, sortBy, sortDirection, currentUser, role);
        return ResponseEntity.ok(companies);
    }

    // 업체 단일 조회 (마스터 관리자, 허브 관리자, 허브 업체)
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompany(
            @PathVariable Long id
    ) {
        CompanyResponse company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    // 업체 수정 (마스터 관리자, 허브 관리자, 허브 업체)
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCompanyRequest updateCompanyRequest
    ) {
        CompanyResponse updatedCompany = companyService.updateCompany(id, updateCompanyRequest);
        return ResponseEntity.ok(updatedCompany);
    }

    // 업체 삭제 (마스터 관리자, 허브 관리자)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}
