package io.fulflix.product.application.strategy;

import io.fulflix.common.web.principal.Role;
import io.fulflix.infra.client.company.CompanyResponse;
import io.fulflix.product.api.dto.UpdateProductRequest;
import io.fulflix.product.application.ProductUpdateStrategy;
import io.fulflix.product.application.validator.ProductValidator;
import io.fulflix.product.domain.Product;
import io.fulflix.product.exception.ProductErrorCode;
import io.fulflix.product.exception.ProductException;
import io.fulflix.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubCompanyUpdateProduct implements ProductUpdateStrategy {
    private final ProductRepo productRepo;
    private final ProductValidator productValidator;

    @Override
    public void updateProduct(Long id, UpdateProductRequest updateProductRequest, Long currentUser, Role role) {
        Product product = productRepo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        CompanyResponse companyResponse = productValidator.checkCompanyExistsForHub(product.getCompanyId());

        if (!companyResponse.getOwnerId().equals(currentUser)) {
            throw new ProductException(ProductErrorCode.UNAUTHORIZED_ACCESS);
        }

        if (updateProductRequest.getProductName() != null && !updateProductRequest.getProductName().equals(product.getProductName())) {
            productValidator.checkProductDuplication(product.getCompanyId(), updateProductRequest.getProductName());
            product.updateProductName(updateProductRequest);
        }

        product.updateStockQuantity(updateProductRequest);

        productRepo.save(product);
    }

    @Override
    public boolean isMatched(Role role) {
        return role.isHubCompany();
    }
}
