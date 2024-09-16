package io.fulflix.product.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.product.domain.Product;
import io.fulflix.product.exception.ProductErrorCode;
import io.fulflix.product.exception.ProductException;
import io.fulflix.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductDeleteService {
    private final ProductRepo productRepo;

    @Transactional
    public void deleteProduct(Long id, Long currentUser, Role role) {
        validateMasterAdminAuthority(role);

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.delete();
    }

    private void validateMasterAdminAuthority(Role role) {
        if (!role.isMasterAdmin()) {
            throw new ProductException(ProductErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
