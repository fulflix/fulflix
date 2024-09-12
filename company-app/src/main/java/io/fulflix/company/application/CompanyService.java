package io.fulflix.company.application;

import io.fulflix.company.api.dto.RegisterCompanyRequest;
import io.fulflix.company.domain.Company;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepo companyRepo;

    // 업체 등록 (마스터 관리자, 허브 관리자)
    public void registerCompany(RegisterCompanyRequest registerCompanyRequest) {
        checkCompanyDuplication(registerCompanyRequest.getCompanyName());
        Company company = RegisterCompanyRequest.toEntity(registerCompanyRequest);
        companyRepo.save(company);
    }

    // 업체 존재 확인
    private Company findCompanyById(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }

    // 업체 중복 확인
    private void checkCompanyDuplication(String companyName) {
        if (companyRepo.findByCompanyName(companyName).isPresent()) {
            throw new CompanyException(CompanyErrorCode.DUPLICATE_COMPANY_NAME);
        }
    }

    // TODO 사용자 존재 확인

    // TODO 허브 존재 확인
}
