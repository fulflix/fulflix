package io.fulflix.company.application.validator;

import feign.FeignException;
import io.fulflix.company.exception.CompanyErrorCode;
import io.fulflix.company.exception.CompanyException;
import io.fulflix.company.repo.CompanyRepo;
import io.fulflix.infra.client.exception.HubErrorCode;
import io.fulflix.infra.client.exception.HubException;
import io.fulflix.infra.client.hub.HubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidator {
    private final HubClient hubClient;
    private final CompanyRepo companyRepo;

    // hubId 존재 확인 예외처리 (FeignClient)
    public void checkHubExists(Long hubId) {
        try {
            hubClient.getHubById(hubId);
        } catch (FeignException.NotFound e) {
            throw new HubException(HubErrorCode.HUB_NOT_FOUND);
        }
    }

    // 업체명 중복 확인
    public void checkCompanyDuplication(String companyName) {
        if (companyRepo.findByCompanyName(companyName).isPresent()) {
            throw new CompanyException(CompanyErrorCode.DUPLICATE_COMPANY_NAME);
        }
    }
}
