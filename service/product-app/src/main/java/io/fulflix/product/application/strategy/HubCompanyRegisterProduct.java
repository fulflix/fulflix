package io.fulflix.product.application.strategy;

import io.fulflix.common.web.principal.Role;
import io.fulflix.infra.client.company.CompanyResponse;
import io.fulflix.infra.client.exception.CompanyErrorCode;
import io.fulflix.infra.client.exception.CompanyException;
import io.fulflix.product.api.dto.RegisterProductRequest;
import io.fulflix.product.application.ProductRegisterStrategy;
import io.fulflix.product.application.validator.ProductValidator;
import io.fulflix.product.domain.Product;
import io.fulflix.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubCompanyRegisterProduct implements ProductRegisterStrategy {

    private final ProductRepo productRepo;
    private final ProductValidator productValidator;

    @Override
    @Transactional
    public void registerProduct(RegisterProductRequest registerProductRequest, Long currentUser, Role role) {
        CompanyResponse companyResponse = productValidator.checkCompanyExistsForHub(
            registerProductRequest.getCompanyId()
        );
        Long companyHubId = companyResponse.getHubId();
        Long companyOwnerId = companyResponse.getOwnerId();

        if (!companyOwnerId.equals(currentUser)) {
            throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
        }

        productValidator.checkProductDuplication(
            registerProductRequest.getCompanyId(),
            registerProductRequest.getProductName()
        );

        Product product = Product.builder()
            .companyId(registerProductRequest.getCompanyId())
            .hubId(companyHubId)
            .productName(registerProductRequest.getProductName())
            .stockQuantity(registerProductRequest.getStockQuantity())
            .build();

        productRepo.save(product);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubCompany();
    }

}
