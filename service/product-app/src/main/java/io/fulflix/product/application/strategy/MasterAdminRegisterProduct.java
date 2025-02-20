package io.fulflix.product.application.strategy;

import io.fulflix.core.web.principal.Role;
import io.fulflix.infra.client.company.CompanyDetailResponse;
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
public class MasterAdminRegisterProduct implements ProductRegisterStrategy {

    private final ProductRepo productRepo;
    private final ProductValidator productValidator;

    @Override
    @Transactional
    public void registerProduct(RegisterProductRequest registerProductRequest, Long currentUser, Role role) {
        CompanyDetailResponse companyDetailResponse = productValidator.checkCompanyExistsForAdmin(
            registerProductRequest.getCompanyId()
        );
        productValidator.checkProductDuplication(
            registerProductRequest.getCompanyId(),
            registerProductRequest.getProductName()
        );

        Product product = Product.builder()
            .companyId(registerProductRequest.getCompanyId())
            .hubId(companyDetailResponse.getHubId())
            .productName(registerProductRequest.getProductName())
            .stockQuantity(registerProductRequest.getStockQuantity())
            .build();

        productRepo.save(product);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isMasterAdmin();
    }

}
