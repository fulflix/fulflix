package io.fulflix.product.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.fulflix.product.domain.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ProductResponse {

    private Long id;
    private Long hubId;
    private Long companyId;
    private String productName;
    private Integer stockQuantity;

    public ProductResponse() {
    }

    @QueryProjection
    public ProductResponse(Long id,
        Long hubId,
        Long companyId,
        String productName,
        Integer stockQuantity) {
        this.id = id;
        this.hubId = hubId;
        this.companyId = companyId;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
    }

    public static ProductResponse fromEntity(Product entity) {
        return ProductResponse.builder()
            .id(entity.getId())
            .hubId(entity.getHubId())
            .companyId(entity.getCompanyId())
            .productName(entity.getProductName())
            .stockQuantity(entity.getStockQuantity())
            .build();
    }

}
