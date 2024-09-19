package io.fulflix.infra.client.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductDetailResponse {

    private Long id;
    private Long hubId;
    private Long companyId;
    private String productName;
    private Integer stockQuantity;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private Long updatedBy;
    private LocalDateTime updatedAt;

}
