package io.fulflix.infra.client.external.naver;

import org.springframework.context.annotation.Bean;

public class NaverDirectionClientHeaderConfig {
    @Bean
    public NaverRequestInterceptor requestInterceptor() {
        return new NaverRequestInterceptor();
    }
}
