package io.fulflix.product.repo;

import static io.fulflix.product.domain.QProduct.product;

import com.querydsl.jpa.JPQLQueryFactory;
import io.fulflix.product.api.dto.ProductResponse;
import io.fulflix.product.api.dto.QProductResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepo {

    private final JPQLQueryFactory queryFactory;

    public Optional<ProductResponse> findProductById(Long id) {
        return Optional.ofNullable(queryFactory.select(
            new QProductResponse(
                product.id,
                product.hubId,
                product.companyId,
                product.productName,
                product.stockQuantity
            ))
            .from(product)
            .where(product.id.eq(id))
            .fetchOne());
    }
}
