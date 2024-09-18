package io.fulflix.infra.client.hub;

import static io.fulflix.infra.client.hub.HubClient.HUB_APP_CLIENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.fulflix.core.app.feign.FeignClientErrorDecoder;
import io.fulflix.core.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = HUB_APP_CLIENT, configuration = {
    FulflixPrincipalRequestHeaderInterceptor.class,
    FeignClientErrorDecoder.class
})
public interface HubClient extends HubService {

    String HUB_APP_CLIENT = "hub-app";
    String HUB_BY_ID_URI = "/hub/{hubId}";

    @GetMapping(
        path = HUB_BY_ID_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    HubResponse getHubById(@PathVariable Long hubId);

}
