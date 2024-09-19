package io.fulflix.order.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.fulflix.order.domain.Order;
import io.fulflix.order.domain.OrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JsonProperty("isDeleted")
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
