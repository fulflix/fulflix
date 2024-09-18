package io.fulflix.infra.client.external.naver;

import static io.fulflix.infra.client.external.naver.NaverDirectionClient.NAVER_DIRECTION_API;
import static io.fulflix.infra.client.external.naver.NaverDirectionClient.NAVER_MAP_DIRECTION_API_URL;

import io.fulflix.core.app.feign.FeignClientErrorDecoder;
import io.fulflix.infra.client.external.naver.dto.RouteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = NAVER_DIRECTION_API,
    url = NAVER_MAP_DIRECTION_API_URL,
    configuration = {
        NaverDirectionClientHeaderConfig.class,
        FeignClientErrorDecoder.class
    }
)
public interface NaverDirectionClient {

    String NAVER_DIRECTION_API = "naver-openapi";
    String NAVER_MAP_DIRECTION_API_URL = "https://naveropenapi.apigw.ntruss.com";

    @GetMapping(path = "/map-direction/v1/driving")
    RouteResponse getDirection(
        @RequestParam String start,
        @RequestParam String goal
    );

}
