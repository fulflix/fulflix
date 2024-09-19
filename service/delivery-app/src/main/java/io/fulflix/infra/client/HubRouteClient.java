package io.fulflix.infra.client;

import static io.fulflix.infra.client.HubClient.HUB_APP_CLIENT;

import io.fulflix.core.app.feign.FeignClientErrorDecoder;
import io.fulflix.core.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import io.fulflix.infra.client.dto.ShortestPathRequest;
import io.fulflix.infra.client.dto.ShortestPathResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = HUB_APP_CLIENT, configuration = {
    FulflixPrincipalRequestHeaderInterceptor.class,
    FeignClientErrorDecoder.class
})
public interface HubRouteClient {

    String HUB_APP_CLIENT = "hub-app";
    String CREATE_HUB_URI = "/hub-route";
    String GET_HUB_DETAIL_URI = "/hub-route/{hubrouteId}";

    // 최단 경로 생성
    @PostMapping("/shortest-path")
    List<ShortestPathResponse> findShortestPath(@RequestBody ShortestPathRequest request);

}
