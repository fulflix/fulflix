package io.fulflix.product.application.validator;

import feign.FeignException;
import io.fulflix.infra.client.company.CompanyClient;
import io.fulflix.infra.client.company.CompanyDetailResponse;
import io.fulflix.infra.client.company.CompanyResponse;
import io.fulflix.infra.client.exception.CompanyErrorCode;
import io.fulflix.infra.client.exception.CompanyException;
import io.fulflix.product.exception.ProductErrorCode;
import io.fulflix.product.exception.ProductException;
import io.fulflix.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepo productRepo;
    private final CompanyClient companyClient;

    // companyId 조회하여 hubId 반환 (마스터 관리자)
    public CompanyDetailResponse checkCompanyExistsForAdmin(Long companyId) {
        try {
            return companyClient.getCompanyByIdForAdmin(companyId);
        } catch (FeignException e) {
            throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
        }
    }

    // companyId 조회하여 hubId 반환 (허브 업체)
    public CompanyResponse checkCompanyExistsForHub(Long companyId) {
        try {
            return companyClient.getCompanyByIdForHub(companyId);
        } catch (FeignException e) {
            throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
        }
    }

    // 업체에 중복된 상품이 있는지 확인
    public void checkProductDuplication(Long companyId, String productName) {
        if (productRepo.findByCompanyIdAndProductName(companyId, productName).isPresent()) {
            throw new ProductException(ProductErrorCode.DUPLICATE_PRODUCT_NAME);
        }
    }
}
