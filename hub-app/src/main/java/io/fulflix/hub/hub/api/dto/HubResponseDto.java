package io.fulflix.hub.hub.api.dto;

import io.fulflix.hub.hub.domain.Hub;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubResponseDto {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public static HubResponseDto of(Hub hub) {
        return HubResponseDto.builder()
            .id(hub.getId())
            .name(hub.getName())
            .address(hub.getAddress())
            .latitude(hub.getLatitude())
            .longitude(hub.getLongitude())
            .build();
    }
}