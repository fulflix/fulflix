package io.fulflix.common.app.feign;

import static io.fulflix.common.app.context.UserContextHolderInterceptor.X_USER_ID;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.fulflix.common.app.context.UserContextHolder;

public class RequestHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(X_USER_ID, String.valueOf(UserContextHolder.getCurrentUser()));
    }

}
