package io.fulflix.core.app.context.interceptor;

import io.fulflix.core.app.context.holder.UserRoleContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserRoleContextHolderInterceptor implements HandlerInterceptor {

    public static final String X_USER_ROLE = "X-User-Role";

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        String userId = getUserRoleFromHeader(request);
        UserRoleContextHolder.setCurrentUserRole(userId);
        return true;
    }

    private String getUserRoleFromHeader(HttpServletRequest request) {
        return request.getHeader(X_USER_ROLE);
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        Exception ex
    ) {
        UserRoleContextHolder.clear();
    }

}
