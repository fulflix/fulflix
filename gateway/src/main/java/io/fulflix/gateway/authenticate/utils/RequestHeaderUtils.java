package io.fulflix.gateway.authenticate.utils;

import io.fulflix.gateway.authenticate.exception.AuthErrorCode;
import io.fulflix.gateway.authenticate.exception.UnAuthorizedException;
import io.fulflix.gateway.authenticate.jwt.FulflixPrincipal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

public class RequestHeaderUtils {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String X_USER_ID = "X-User-Id";
    private static final String X_USER_USERNAME = "X-User-Username";
    private static final String X_USER_ROLE = "X-User-Role";
    private static final String ANONYMOUS = "anonymous";

    public static String extractAccessToken(ServerWebExchange exchange) {
        String authorizationHeader = extractAuthorizationHeader(exchange);
        validateAuthorizationHeader(authorizationHeader);

        return extractJwt(authorizationHeader);
    }

    public static String extractAuthorizationHeader(ServerWebExchange exchange) {
        return exchange.getRequest()
            .getHeaders()
            .getFirst(HttpHeaders.AUTHORIZATION);
    }

    private static void validateAuthorizationHeader(String authorizationHeader) {
        if (hasNoAuthorizationHeader(authorizationHeader)) {
            throw new UnAuthorizedException(AuthErrorCode.HAS_NO_AUTHORIZATION_HEADER);
        }
    }

    public static boolean hasNoAuthorizationHeader(String authorizationHeader) {
        return !StringUtils.hasText(authorizationHeader);
    }

    private static String extractJwt(String authorizationHeader) {
        checkIsBearerToken(authorizationHeader);
        return authorizationHeader.substring(BEARER_PREFIX.length());
    }

    private static void checkIsBearerToken(String authorizationHeader) {
        if (isNotBearerToken(authorizationHeader)) {
            throw new UnAuthorizedException(AuthErrorCode.IS_NOT_BEARER_TOKEN);
        }
    }

    private static boolean isNotBearerToken(String authorizationHeader) {
        return !authorizationHeader.startsWith(BEARER_PREFIX);
    }

    public static ServerWebExchange setPrincipalToRoutingHeader(
        ServerWebExchange exchange,
        FulflixPrincipal principal
    ) {
        ServerHttpRequest request = exchange.getRequest()
            .mutate()
            .header(X_USER_ID, String.valueOf(principal.id()))
            .header(X_USER_USERNAME, principal.username())
            .header(X_USER_ROLE, String.valueOf(principal.roles()))
            .build();
        return exchange.mutate().request(request).build();
    }

    public static ServerWebExchange setAnonymousToRoutingHeader(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest()
            .mutate()
            .header(X_USER_ID, ANONYMOUS)
            .build();
        return exchange.mutate().request(request).build();
    }

}
