package io.fulflix.auth.utils.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    @Value("${spring.application.name}") String issuer,
    String audience,
    int expirationMinutes,
    String secretKey
) {

}
