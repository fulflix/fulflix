package io.fulflix.auth.utils.jwt;

import static io.fulflix.auth.utils.jwt.SigningUtils.PRINCIPAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.fulflix.auth.domain.FulflixPrincipal;
import io.jsonwebtoken.Claims;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Utils:JWT:JwtProvider")
class JwtProviderTest extends TokenTestHelper {

    @Test
    @DisplayName("JWT 생성")
    void create() {
        // When
        String given = JwtProvider.create(TEST_JWT_PROPERTIES, TEST_PRINCIPAL);

        // Then
        Claims actual = parse(given, TEST_JWT_PROPERTIES);

        assertAll(
            () -> assertThat(actual.getIssuer()).as("토큰 발급처 검증")
                .isEqualTo(TEST_JWT_PROPERTIES.issuer()),
            () -> assertThat(actual.getAudience()).as("토큰 발급 대상자 검증")
                .isEqualTo(Set.of(TEST_JWT_PROPERTIES.audience())),
            () -> assertThat(actual.getExpiration()).as("토큰 유효기간 검증")
                .isEqualTo(extractExpiration(actual.getIssuedAt())),
            () -> assertThat(actual.get(PRINCIPAL, FulflixPrincipal.class)).as("principal 검증")
                .isInstanceOf(TEST_PRINCIPAL.getClass())
        );
    }

}
