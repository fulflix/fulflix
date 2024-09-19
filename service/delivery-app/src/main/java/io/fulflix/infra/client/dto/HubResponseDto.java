package io.fulflix.infra.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = HubResponseDto.HubResponseDtoBuilder.class)
public class HubResponseDto {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    @JsonPOJOBuilder(withPrefix = "")
    public static class HubResponseDtoBuilder {
    }
}