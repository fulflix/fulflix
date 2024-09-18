package io.fulflix.infra.client.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductResponse {
    private Long id;
    private Long hubId;
    private Long companyId;
    private String ProductName;
    private Integer stockQuantity;
    private boolean isDeleted;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
