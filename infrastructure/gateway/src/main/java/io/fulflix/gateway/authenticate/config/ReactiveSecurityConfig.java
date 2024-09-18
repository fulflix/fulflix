package io.fulflix.gateway.authenticate.config;

import static io.fulflix.gateway.authenticate.domain.Role.MASTER_ADMIN;
import static io.fulflix.gateway.authenticate.domain.Role.getAllRoles;
import static org.springframework.http.HttpMethod.*;

import io.fulflix.gateway.authenticate.jwt.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ReactiveSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http,
        JwtProperties jwtProperties
    ) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

            // TODO 권한 별 Endpoint 관리를 위한 Enum 정의
            .authorizeExchange(exchanges ->
                exchanges
                    .pathMatchers(POST, "/auth/**").permitAll()
                    .pathMatchers(GET, "/user/**").hasAnyAuthority(getAllRoles())
                    .pathMatchers(POST, "/user/**").hasAnyAuthority(MASTER_ADMIN.name())

                    .pathMatchers("/company/**").hasAnyAuthority(getAllRoles())

                    .pathMatchers(GET,"/hub/**").hasAnyAuthority(getAllRoles())
                    .pathMatchers(POST,"/hub/**").hasAnyAuthority(MASTER_ADMIN.name())
                    .pathMatchers(PUT,"/hub/**").hasAnyAuthority(MASTER_ADMIN.name())
                    .pathMatchers(DELETE,"/hub/**").hasAnyAuthority(MASTER_ADMIN.name())

                    .pathMatchers(GET,"/hub-route/**").hasAnyAuthority(getAllRoles())
                    .pathMatchers(POST,"/hub-route/**").hasAnyAuthority(MASTER_ADMIN.name())
                    .pathMatchers(PUT,"/hub-route/**").hasAnyAuthority(MASTER_ADMIN.name())
                    .pathMatchers(DELETE,"/hub-route/**").hasAnyAuthority(MASTER_ADMIN.name())

                    .pathMatchers("/delivery/**").hasAnyAuthority(getAllRoles())
                    .pathMatchers("/delivery-route/**").hasAnyAuthority(getAllRoles())

                    .anyExchange().authenticated()
            )
            .securityContextRepository(securityContextRepository(jwtProperties))
            .build();
    }

    @Bean
    public JwtReactiveSecurityContextRepository securityContextRepository(JwtProperties jwtProperties) {
        return new JwtReactiveSecurityContextRepository(jwtProperties);
    }

}
