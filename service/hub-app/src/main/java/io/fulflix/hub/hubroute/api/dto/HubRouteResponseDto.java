package io.fulflix.hub.hubroute.api.dto;

import io.fulflix.hub.hub.api.dto.HubResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubRouteResponseDto {

    private Long id;
    private HubResponseDto departureHub;
    private HubResponseDto arrivalHub;
    private Long duration;
    private Double distance;
    private String route;
    private Boolean isDeleted;

}
