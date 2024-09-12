package io.fulflix.common.app.feign;

import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    private static final String FEIGN_CLIENT_PACKAGE = "infra.client";
    public static final String FEIGN_CLIENT_BASE_PACKAGE =
        BASE_PACKAGE + "." + FEIGN_CLIENT_PACKAGE;

    @Bean
    public RequestHeaderInterceptor requestHeaderInterceptor() {
        return new RequestHeaderInterceptor();
    }

}
