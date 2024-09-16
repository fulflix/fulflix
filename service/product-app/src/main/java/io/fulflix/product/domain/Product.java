package io.fulflix.product.domain;

import io.fulflix.product.config.ProductAuditable;
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
}
