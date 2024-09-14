package io.fulflix.gateway.authenticate.config;

import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.extractAccessToken;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.extractAuthorizationHeader;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.hasNoAuthorizationHeader;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.setAnonymousToRoutingHeader;
import static io.fulflix.gateway.authenticate.utils.RequestHeaderUtils.setPrincipalToRoutingHeader;

import io.fulflix.gateway.authenticate.exception.UnAuthorizedException;
import io.fulflix.gateway.authenticate.domain.FulflixPrincipal;
import io.fulflix.gateway.authenticate.jwt.JwtProperties;
import io.fulflix.gateway.authenticate.jwt.JwtResolver;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtReactiveSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtProperties jwtProperties;

    public JwtReactiveSecurityContextRepository(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        if(isAnonymousRequest(exchange)){
            setAnonymousToRoutingHeader(exchange);
            return Mono.empty();
        }

        try {
            String jwt = extractAccessToken(exchange);
            FulflixPrincipal principal = JwtResolver.resolve(jwt, jwtProperties);
            setPrincipalToRoutingHeader(exchange, principal);
            SecurityContext securityContext = generateSecurityContext(principal);

            return Mono.justOrEmpty(securityContext);
        } catch (UnAuthorizedException unAuthorizedException) {
            return Mono.error(unAuthorizedException);
        }

    }

    private boolean isAnonymousRequest(ServerWebExchange exchange) {
        String authorizationHeader = extractAuthorizationHeader(exchange);
        return hasNoAuthorizationHeader(authorizationHeader);
    }

    private SecurityContext generateSecurityContext(FulflixPrincipal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(
            principal);

        return new SecurityContextImpl(authenticationToken);
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(
        FulflixPrincipal principal
    ) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(principal.roles().name());

        return new UsernamePasswordAuthenticationToken(
            principal,
            null,
            List.of(authority)
        );
    }

}
