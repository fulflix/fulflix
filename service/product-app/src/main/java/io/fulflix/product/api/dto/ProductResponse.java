package io.fulflix.product.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.fulflix.infra.client.company.CompanyResponse;
import io.fulflix.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private Long hubId;
    private Long companyId;
    private String ProductName;
    private Integer stockQuantity;

    @QueryProjection
    public ProductResponse() {
    }

    public static ProductResponse fromEntity(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .hubId(entity.getHubId())
                .companyId(entity.getCompanyId())
                .ProductName(entity.getProductName())
                .stockQuantity(entity.getStockQuantity())
                .build();
    }
}
