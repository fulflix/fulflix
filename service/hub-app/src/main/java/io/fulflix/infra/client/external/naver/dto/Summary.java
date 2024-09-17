package io.fulflix.infra.client.external.naver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {

    private Location start;
    private Location goal;
    private int distance;
    private int duration;

}
