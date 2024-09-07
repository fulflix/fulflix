package io.fulflix.auth.utils.jwt;

import static io.fulflix.auth.utils.jwt.SigningUtils.PRINCIPAL;
import static io.fulflix.auth.utils.jwt.SigningUtils.generateSigningKey;

import io.fulflix.auth.domain.FulflixPrincipal;
import io.fulflix.auth.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

public class TokenTestHelper {

    protected static final Long ID = 1L;
    protected static final String USERNAME = "hong-gd";
    protected static final Role ROLES = Role.MASTER_ADMIN;

    protected static final String ISSUER = "auth-app";
    protected static final String AUDIENCE = "fulflix-service-app";
    protected static final String TEST_PLAIN_SECRET_KEY = "test-fulflix-plain-secret-key";
    protected static final int EXPIRATION_MINUTES = 10;

    protected static FulflixPrincipal TEST_PRINCIPAL = new FulflixPrincipal(ID, USERNAME, ROLES);
    protected static JwtProperties TEST_JWT_PROPERTIES = new JwtProperties(
        ISSUER,
        AUDIENCE,
        EXPIRATION_MINUTES,
        TEST_PLAIN_SECRET_KEY
    );

    protected Instant extractExpiration(Date issuedAt) {
        return Instant.from(issuedAt.toInstant())
            .plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES);
    }

    protected Claims parse(String jwt, JwtProperties jwtProperties) {
        SecretKey signingKey = generateSigningKey(jwtProperties.secretKey());

        return Jwts.parser()
            .json(jacksonDeserializer)
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(jwt)
            .getPayload();
    }

    private static final JacksonDeserializer jacksonDeserializer =
        new JacksonDeserializer<Map<String, FulflixPrincipal>>(
            Maps.<String, Class<?>>of(PRINCIPAL, FulflixPrincipal.class).build());

}
