package io.fulflix.product.repo;

import io.fulflix.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByCompanyIdAndProductName(Long companyId, String productName);
}
