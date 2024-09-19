package io.fulflix.order.api.dto;

import io.fulflix.infra.client.delivery.DeliveryRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierCreateOrderRequest {
    @NotNull(message = "Receiver Id를 입력해 주세요.")
    private Long receiverId;

    @NotNull(message = "Product Id를 입력해 주세요.")
    private Long productId;

    @NotNull(message = "주문 수량을 입력해 주세요.")
    @Min(value = 1, message = "최소 주문 수량은 1개입니다.")
    private Integer orderQuantity;

    @NotNull(message = "배송 정보를 입력해 주세요.")
    private DeliveryRequest deliveryRequest;
}
