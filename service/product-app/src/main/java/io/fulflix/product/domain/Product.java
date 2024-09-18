package io.fulflix.product.domain;

import io.fulflix.product.api.dto.UpdateProductRequest;
import io.fulflix.product.config.ProductAuditable;
import io.fulflix.product.exception.ProductErrorCode;
import io.fulflix.product.exception.ProductException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "p_product")
public class Product extends ProductAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hub_id", nullable = false)
    private Long hubId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    // productName 업데이트
    public void updateProductName(UpdateProductRequest updateProductRequest) {
        this.productName = updateProductRequest.getProductName();
    }

    // stockQuantity 업데이트
    public void updateStockQuantity(UpdateProductRequest updateProductRequest) {
        this.stockQuantity = updateProductRequest.getStockQuantity();
    }

    // 주문 생성 시, 재고 감소
    public void reduceStock(int orderQuantity) {
        if (this.stockQuantity < orderQuantity) {
            throw new ProductException(ProductErrorCode.INSUFFICIENT_STOCK);
        }
        this.stockQuantity -= orderQuantity;
    }
}
