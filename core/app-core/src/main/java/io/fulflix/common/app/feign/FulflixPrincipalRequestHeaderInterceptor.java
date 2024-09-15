package io.fulflix.common.app.feign;

import static io.fulflix.common.app.context.interceptor.UserContextHolderInterceptor.X_USER_ID;
import static io.fulflix.common.app.context.interceptor.UserRoleContextHolderInterceptor.X_USER_ROLE;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.fulflix.common.app.context.holder.UserContextHolder;
import io.fulflix.common.app.context.holder.UserRoleContextHolder;

public class FulflixPrincipalRequestHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(X_USER_ID, String.valueOf(UserContextHolder.getCurrentUser()));
        requestTemplate.header(X_USER_ROLE, String.valueOf(UserRoleContextHolder.getCurrentUserRole()));
    }

}
