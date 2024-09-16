package io.fulflix.product.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.product.api.dto.ProductDetailResponse;
import io.fulflix.product.api.dto.ProductResponse;
import io.fulflix.product.domain.Product;
import io.fulflix.product.exception.ProductErrorCode;
import io.fulflix.product.exception.ProductException;
import io.fulflix.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepo productRepo;

    // 상품 삭제 (마스터 관리자)
    @Transactional
    public void deleteProduct(Long id, Long currentUser, Role role) {
        validateMasterAdminAuthority(role);

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.delete();
    }

    // 상품 전체 조회 및 검색 (마스터 관리자)
    public Page<ProductDetailResponse> getAllProductsForAdmin(String product, Integer stockQuantity, Pageable pageable, Long currentUser, Role role) {
        validateMasterAdminAuthority(role);

        if (pageable.getPageSize() != 10 && pageable.getPageSize() != 30 && pageable.getPageSize() != 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        }

        // 삭제된 업체까지 모두 조회
        Page<Product> products;

        // 상품명, 재고
        if (product != null && !product.isEmpty() && stockQuantity != null && stockQuantity > 0) {
            products = productRepo.findByProductNameContainingAndStockQuantityGreaterThanEqual(product, stockQuantity, pageable);
        } else if (product != null && !product.isEmpty()) { // 상품명
            products = productRepo.findByProductNameContaining(product, pageable);
        } else if (stockQuantity != null && stockQuantity > 0) { // 재고
            products = productRepo.findByStockQuantityGreaterThanEqual(stockQuantity, pageable);
        } else {
            products = productRepo.findAll(pageable);
        }

        return products.map(ProductDetailResponse::fromEntity);
    }

    public Page<ProductResponse> getAllProductsForHub(String product, Integer stockQuantity, Pageable pageable, Long currentUser, Role role) {
        validateMasterHubAuthority(role);

        if (pageable.getPageSize() != 10 && pageable.getPageSize() != 30 && pageable.getPageSize() != 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        }

        Page<Product> products;

        if (product != null && !product.isEmpty() && stockQuantity != null && stockQuantity > 0) {
            products = productRepo.findByProductNameContainingAndStockQuantityGreaterThanEqualAndIsDeletedFalse(product, stockQuantity, pageable);
        } else if (product != null && !product.isEmpty()) {
            products = productRepo.findByProductNameContainingAndIsDeletedFalse(product, pageable);
        } else if (stockQuantity != null && stockQuantity > 0) {
            products = productRepo.findByStockQuantityGreaterThanEqualAndIsDeletedFalse(stockQuantity, pageable);
        } else {
            products = productRepo.findByIsDeletedFalse(pageable);
        }

        return products.map(ProductDetailResponse::fromEntity);
    }

    private void validateMasterAdminAuthority(Role role) {
        if (!role.isMasterAdmin()) {
            throw new ProductException(ProductErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    private void validateMasterHubAuthority(Role role) {
        if (role.isMasterAdmin()) {
            throw new ProductException(ProductErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
