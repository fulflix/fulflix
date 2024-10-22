package io.fulflix.order.domain;

import io.fulflix.core.app.jpa.audit.Auditable;
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
@Table(name = "p_order")
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    // 주문 수량 업데이트
    public void updateOrderQuantity(int newQuantity) {
        this.orderQuantity = newQuantity;
    }
}
