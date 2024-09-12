package io.fulflix.gateway.authenticate.filter;

import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.extractAuthorizationHeader;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.hasNoAuthorizationHeader;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.setAnonymousToRoutingHeader;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.setPrincipalToRoutingHeader;

import io.fulflix.gateway.authenticate.jwt.FulflixPrincipal;
import io.fulflix.gateway.authenticate.jwt.JwtProperties;
import io.fulflix.gateway.authenticate.jwt.JwtResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AllowsAllRequestFilter extends
    AbstractGatewayFilterFactory<AllowsAllRequestFilter.Config> {

    private final JwtProperties jwtProperties;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String jwt = extractAuthorizationHeader(exchange);
            if (hasNoAuthorizationHeader(jwt)) {
                setAnonymousToRoutingHeader(exchange);
            } else {
                FulflixPrincipal principal = JwtResolver.resolve(jwt, jwtProperties);
                setPrincipalToRoutingHeader(exchange, principal);
            }

            return chain.filter(exchange);
        };
    }


    public AllowsAllRequestFilter(JwtProperties jwtProperties) {
        super(Config.class);
        this.jwtProperties = jwtProperties;
    }

    public static class Config {

    }

}
