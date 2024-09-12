package io.fulflix.gateway.authenticate.filter;

import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.extractAccessToken;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.setPrincipalToRoutingHeader;

import io.fulflix.gateway.authenticate.exception.UnAuthorizedException;
import io.fulflix.gateway.authenticate.filter.JwtAuthorizationFilter.Config;
import io.fulflix.gateway.authenticate.jwt.FulflixPrincipal;
import io.fulflix.gateway.authenticate.jwt.JwtProperties;
import io.fulflix.gateway.authenticate.jwt.JwtResolver;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<Config> {

    private final JwtProperties jwtProperties;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            try {
                String jwt = extractAccessToken(exchange);
                FulflixPrincipal principal = JwtResolver.resolve(jwt, jwtProperties);
                setPrincipalToRoutingHeader(exchange, principal);
            } catch (UnAuthorizedException exception) {
                return onError(exchange, exception);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(
        ServerWebExchange exchange,
        UnAuthorizedException unAuthorizedException
    ) {
        ServerHttpRequest request = exchange.getRequest();
        String id = request.getId();
        String method = request.getMethod().name();
        URI uri = request.getURI();
        HttpStatus status = unAuthorizedException.getStatus();
        String message = unAuthorizedException.getMessage();

        log.error("[{}][{}][{}][{}][{}]", id, method, uri, status, message);

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public JwtAuthorizationFilter(JwtProperties jwtProperties) {
        super(Config.class);
        this.jwtProperties = jwtProperties;
    }

    public static class Config {

    }

}
