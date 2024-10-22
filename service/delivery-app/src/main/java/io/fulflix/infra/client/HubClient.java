package io.fulflix.infra.client;

import io.fulflix.core.app.feign.FeignClientErrorDecoder;
import io.fulflix.core.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import io.fulflix.infra.client.dto.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = HubClient.HUB_APP_CLIENT, configuration = {
        FulflixPrincipalRequestHeaderInterceptor.class,
        FeignClientErrorDecoder.class
})
public interface HubClient {
    String HUB_APP_CLIENT = "hub-app";
    String GET_HUB_DETAIL_URI = "/hub/{hubId}";

    @GetMapping(
            path = GET_HUB_DETAIL_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    HubResponseDto getHub(@PathVariable Long hubId);
}
