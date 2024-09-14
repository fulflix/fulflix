package io.fulflix.infra.client.hub;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HubResponse {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}
