package io.fulflix.product.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterProductRequest {
    @NotNull(message = "Company Id를 입력해 주세요.")
    private Long companyId;

    @NotBlank(message = "상품명을 입력해 주세요.")
    private String productName;

    @NotNull(message = "재고를 입력해 주세요.")
    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stockQuantity;
}
