package io.fulflix.hub.application.dto;

import io.fulflix.hub.domain.Hub;
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
        return Hub.builder()
            .name(dto.getName())
            .address(dto.getAddress())
            .latitude(dto.getLatitude())
            .longitude(dto.getLongitude())
            .build();
    }
}