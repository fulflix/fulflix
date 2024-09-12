package io.fulflix.gateway.authenticate.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String audience,
    int expirationMinutes,
    String secretKey
) {

}
