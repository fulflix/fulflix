package io.fulflix.hub.hubroute.api.dto;

import io.fulflix.hub.hub.api.dto.HubResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubRouteResponseDto {
    private Long id;
    private HubResponseDto departureHub;
    private HubResponseDto arrivalHub;
    private Integer duration;
    private String route;
    private boolean isDeleted;
}
