package io.fulflix.product.api.dto;

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

    @NotNull(message = "업체 타입을 입력해 주세요.")
    private int stockQuantity;
}
