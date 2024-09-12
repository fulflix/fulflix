package io.fulflix.common.app.context.interceptor;

import io.fulflix.common.app.context.holder.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

// TODO web-core 계층으로 이동
public class UserContextHolderInterceptor implements HandlerInterceptor {

    public static final String X_USER_ID = "X-User-Id";

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        String userId = getUserIdFromHeader(request);
        UserContextHolder.setCurrentUser(userId);
        return true;
    }

    private String getUserIdFromHeader(HttpServletRequest request) {
        return request.getHeader(X_USER_ID);
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        Exception ex
    ) {
        UserContextHolder.clear();
    }

}
