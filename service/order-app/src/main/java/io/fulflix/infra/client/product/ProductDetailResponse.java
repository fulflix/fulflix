package io.fulflix.infra.client.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductDetailResponse {

    private Long id;
    private Long hubId;
    private Long companyId;
    private String productName;
    private Integer stockQuantity;
    private Boolean isDeleted;
    private Long updatedBy;
    private LocalDateTime updatedAt;

}
