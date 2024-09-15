package io.fulflix.infra.client.external.naver.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Location {

    private List<Double> location;
    private int dir;

}
