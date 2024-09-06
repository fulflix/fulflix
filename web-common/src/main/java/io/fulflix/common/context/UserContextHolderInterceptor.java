package io.fulflix.common.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

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
