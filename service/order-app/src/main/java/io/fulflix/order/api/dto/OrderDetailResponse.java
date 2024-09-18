package io.fulflix.order.api.dto;

import io.fulflix.order.domain.Order;
import io.fulflix.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private Long supplierId;
    private Long receiverId;
    private Long productId;
    private Integer orderQuantity;
    private OrderStatus orderStatus;
    private boolean isDeleted;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public static OrderDetailResponse fromEntity(Order entity) {
        return OrderDetailResponse.builder()
                .id(entity.getId())
                .supplierId(entity.getSupplierId())
                .receiverId(entity.getReceiverId())
                .productId(entity.getProductId())
                .orderQuantity(entity.getOrderQuantity())
                .orderStatus(entity.getOrderStatus())
                .isDeleted(entity.isDeleted())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
