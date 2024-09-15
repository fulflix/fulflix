package io.fulflix.infra.client.external.naver;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class NaverRequestInterceptor implements RequestInterceptor {

    private static final String X_NCP_APIGW_API_KEY_ID = "X-NCP-APIGW-API-KEY-ID";
    private static final String X_NCP_APIGW_API_KEY = "X-NCP-APIGW-API-KEY";

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(X_NCP_APIGW_API_KEY_ID, clientId);
        requestTemplate.header(X_NCP_APIGW_API_KEY, clientSecret);
    }

}
