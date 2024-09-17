package io.fulflix.product.api.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    private String productName;

    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stockQuantity;
}
