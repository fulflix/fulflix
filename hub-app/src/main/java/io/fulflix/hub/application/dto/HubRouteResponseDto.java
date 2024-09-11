package io.fulflix.hub.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubRouteResponseDto {
    private Long id;
    private Long departureHubId;
    private Long arrivalHubId;
    private Integer duration;
    private String route;
    private boolean isDeleted;
}
