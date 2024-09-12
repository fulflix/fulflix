package io.fulflix.company.api;

import io.fulflix.company.api.dto.RegisterCompanyRequest;
import io.fulflix.company.application.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
