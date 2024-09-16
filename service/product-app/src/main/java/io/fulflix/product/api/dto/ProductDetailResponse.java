package io.fulflix.product.api.dto;

import io.fulflix.infra.client.company.CompanyResponse;
import io.fulflix.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse extends ProductResponse {
    private boolean isDeleted;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public static ProductDetailResponse fromEntity(Product entity) {
        return ProductDetailResponse.builder()
                .id(entity.getId())
                .hubId(entity.getHubId())
                .companyId(entity.getCompanyId())
                .ProductName(entity.getProductName())
                .stockQuantity(entity.getStockQuantity())
                .isDeleted(entity.isDeleted())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
