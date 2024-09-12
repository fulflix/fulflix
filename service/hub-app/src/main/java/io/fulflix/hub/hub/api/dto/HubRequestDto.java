package io.fulflix.hub.hub.api.dto;

import io.fulflix.hub.hub.domain.Hub;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubRequestDto {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public static Hub toEntity(HubRequestDto dto) {
        return new Hub(dto.getName(), dto.getAddress(), dto.getLatitude(), dto.getLongitude());
    }
}