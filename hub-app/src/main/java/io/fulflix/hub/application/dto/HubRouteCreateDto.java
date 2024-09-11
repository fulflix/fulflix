package io.fulflix.hub.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HubRouteCreateDto {
    private Long departureHubId;
    private Long arrivalHubId;
    private Integer duration;
}