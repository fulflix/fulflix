package io.fulflix.infra.client.dto;

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
}