package io.fulflix.product.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.product.api.dto.ProductDetailResponse;
import io.fulflix.product.api.dto.ProductResponse;
import io.fulflix.product.api.dto.ReduceStockRequest;
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
@Transactional(readOnly = true)
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

    // 단일 상품 조회 (마스터 관리자)
    public ProductDetailResponse getProductForAdmin(Long id, Long currentUser, Role role) {
        validateMasterAdminAuthority(role);

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return ProductDetailResponse.fromEntity(product);
    }

    // 단일 상품 조회 (허브 관리자, 허브 업체, 허브 배송 담당자, 업체 배송 담당자)
    public ProductResponse getProductForHub(Long id, Long currentUser, Role role) {
        validateMasterHubAuthority(role);

        Product product = productRepo.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return ProductResponse.fromEntity(product);
    }

    // 주문 생성 시, 재고 감소
    @Transactional
    public void reduceStock(Long id, Long currentUser, ReduceStockRequest reduceStockRequest, Role role) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.reduceStock(reduceStockRequest.getOrderQuantity());
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
