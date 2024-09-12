package io.fulflix.company.api;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.company.api.dto.CompanyResponse;
import io.fulflix.company.api.dto.RegisterCompanyRequest;
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
            @Valid @RequestBody RegisterCompanyRequest registerCompanyRequest
    ) {
        companyService.registerCompany(registerCompanyRequest);
        return created("/company");
    }

    // 업체 전체 조회 및 검색 (마스터 관리자, 허브 관리자)
    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false, defaultValue = "") String query, // 검색
            @RequestParam(defaultValue = "10") int size, // 페이지 크기
            @RequestParam(defaultValue = "createdAt") String sortBy, // 정렬 기준
            @RequestParam(defaultValue = "desc") String sortDirection, // 정렬 방향
            @RequestParam(defaultValue = "0") int page // 페이지 번호
    ) {
        Page<CompanyResponse> companies = companyService.getAllCompanies(query, page, size, sortBy, sortDirection);
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
}
