package io.fulflix.auth.utils.jwt;

import static io.fulflix.auth.utils.jwt.SigningUtils.PRINCIPAL;

import io.fulflix.auth.domain.FulflixPrincipal;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtProvider {

    public static String create(
        JwtProperties jwtProperties,
        FulflixPrincipal principal
    ) {
        SecretKey signingKey = SigningUtils.generateSigningKey(jwtProperties.secretKey());
        Instant issuedAt = Instant.now();
        Date expirationDate = getExpirationDate(issuedAt, jwtProperties.expirationMinutes());

        return Jwts.builder()
            .issuer(jwtProperties.issuer())
            .issuedAt(Date.from(issuedAt))
            .audience().add(jwtProperties.audience())
            .and()
            .claim(PRINCIPAL, principal)
            .expiration(expirationDate)
            .signWith(signingKey)
            .compact();
    }

    private static Date getExpirationDate(Instant issuedAt, int expirationMinutes) {
        Instant expriationInstant = issuedAt
            .plus(expirationMinutes, ChronoUnit.MINUTES);
        return Date.from(expriationInstant);
    }

}
