package io.fulflix.gateway.authenticate.jwt;

import static io.fulflix.gateway.authenticate.utils.SigningUtils.PRINCIPAL;

import io.fulflix.gateway.authenticate.exception.AuthErrorCode;
import io.fulflix.gateway.authenticate.exception.UnAuthorizedException;
import io.fulflix.gateway.authenticate.domain.FulflixPrincipal;
import io.fulflix.gateway.authenticate.utils.SigningUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.WeakKeyException;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;

public class JwtResolver {

    private static final JacksonDeserializer jacksonDeserializer =
        new JacksonDeserializer<Map<String, FulflixPrincipal>>(
            Maps.<String, Class<?>>of(PRINCIPAL, FulflixPrincipal.class).build()
        );

    public static FulflixPrincipal resolve(String token, JwtProperties jwtProperties) {
        Claims claims = parse(token, jwtProperties);
        validateAudience(claims, jwtProperties.audience());

        return extractPrincipal(claims);
    }

    private static void validateAudience(Claims claims, String audience) {
        Set<String> audiences = claims.getAudience();

        if (!audiences.contains(audience)) {
            throw new UnAuthorizedException(AuthErrorCode.UNSUPPORTED_AUDIENCE);
        }
    }

    public static Claims parse(String token, JwtProperties jwtProperties) {
        try {
            SecretKey signingKey = SigningUtils.generateSigningKey(jwtProperties.secretKey());

            return Jwts.parser()
                .json(jacksonDeserializer)
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (WeakKeyException | NullPointerException weakKeyException) {
            throw new UnAuthorizedException(AuthErrorCode.INVALID_SIGNATURE);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new UnAuthorizedException(AuthErrorCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException malformedJwtException) {
            throw new UnAuthorizedException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        } catch (Exception unexpectedException) {
            throw new RuntimeException(unexpectedException);
        }
    }

    private static FulflixPrincipal extractPrincipal(Claims claims) {
        return claims.get(PRINCIPAL, FulflixPrincipal.class);
    }

}
