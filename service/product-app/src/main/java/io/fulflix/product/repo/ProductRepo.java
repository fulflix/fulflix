package io.fulflix.product.repo;

import io.fulflix.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByCompanyIdAndProductName(Long companyId, String productName);
    // 전체 조회 및 검색 (관리자용 - 삭제 포함)
    Page<Product> findByProductNameContainingAndStockQuantityGreaterThanEqual(String product, Integer stockQuantity, Pageable pageable);
    Page<Product> findByProductNameContaining(String product, Pageable pageable);
    Page<Product> findByStockQuantityGreaterThanEqual(Integer stockQuantity, Pageable pageable);
    // 전체 조회 및 검색 (삭제 미포함)
    Page<Product> findByProductNameContainingAndStockQuantityGreaterThanEqualAndIsDeletedFalse(String product, Integer stockQuantity, Pageable pageable);
    Page<Product> findByProductNameContainingAndIsDeletedFalse(String product, Pageable pageable);
    Page<Product> findByStockQuantityGreaterThanEqualAndIsDeletedFalse(Integer stockQuantity, Pageable pageable);
    Page<Product> findByIsDeletedFalse(Pageable pageable);
    // 단일 조회 (삭제 미포함)
    Optional<Product> findByIdAndIsDeletedFalse(Long id);
}
