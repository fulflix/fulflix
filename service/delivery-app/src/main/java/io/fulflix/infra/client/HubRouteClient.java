package io.fulflix.infra.client;

import io.fulflix.common.app.feign.FeignClientConfig;
import io.fulflix.infra.client.dto.ShortestPathRequest;
import io.fulflix.infra.client.dto.ShortestPathResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static io.fulflix.infra.client.HubClient.HUB_APP_CLIENT;

@FeignClient(name = HUB_APP_CLIENT, configuration = FeignClientConfig.class)
public interface HubRouteClient {
    String HUB_APP_CLIENT = "hub-app";
    String CREATE_HUB_URI = "/hub-route";
    String GET_HUB_DETAIL_URI = "/hub-route/{hubrouteId}";

    // 최단 경로 생성
    @PostMapping("/hub-route/shortest-path")
    List<ShortestPathResponse> findShortestPath(@RequestBody ShortestPathRequest request);

}
