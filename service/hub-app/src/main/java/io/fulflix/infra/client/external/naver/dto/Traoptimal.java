package io.fulflix.infra.client.external.naver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Traoptimal {

    private Summary summary;

    public int getDistance() {
        return summary.getDistance();
    }

    public int getDuration() {
        return summary.getDuration();
    }
}
