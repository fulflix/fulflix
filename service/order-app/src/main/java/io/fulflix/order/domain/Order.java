package io.fulflix.order.domain;

import io.fulflix.core.app.jpa.audit.Auditable;
import io.fulflix.order.api.dto.AdminCreateOrderRequest;
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

    // 주문 생성 이벤트 발행
    public AdminCreateOrderRequest createOrderCreatedEvent() {
        return new AdminCreateOrderRequest(
                this.id,
                this.supplierId,
                this.receiverId,
                this.productId,
                this.orderQuantity
        );
    }

    // 주문 생성 시 기본 상태 PENDING 설정
    @PrePersist
    public void prePersist() {
        this.orderStatus = OrderStatus.PENDING;
    }
}
