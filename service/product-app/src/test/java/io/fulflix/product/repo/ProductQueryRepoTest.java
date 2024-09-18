package io.fulflix.product.repo;

import io.fulflix.core.app.base.persistence.JpaTestBase;
import io.fulflix.product.api.dto.ProductResponse;
import io.fulflix.product.config.TestQueryDslConfig;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

@Import(TestQueryDslConfig.class)
@DisplayName("Repo:Query:Product")
class ProductQueryRepoTest extends JpaTestBase {
    private final ProductQueryRepo productQueryRepo;

    public ProductQueryRepoTest(ProductQueryRepo productQueryRepo) {
        this.productQueryRepo = productQueryRepo;
    }
    
    @Test
    @DisplayName("상품 상세 조회")
    void findProductById() {
        // When
        Optional<ProductResponse> productById = productQueryRepo.findProductById(1L);

        // Then
    }

}
