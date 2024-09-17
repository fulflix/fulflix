package io.fulflix.infra.client.external.naver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteResponse {

    private int code;
    private String message;
    @JsonProperty("route")
    private RouteUnit routeUnit;

}
