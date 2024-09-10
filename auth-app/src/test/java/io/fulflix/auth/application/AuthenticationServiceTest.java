package io.fulflix.auth.application;

import static io.fulflix.auth.fixture.AuthTestFixture.SIGN_IN_REQUEST;
import static io.fulflix.auth.fixture.AuthTestFixture.TEST_FULFLIX_PRINCIPAL;
import static io.fulflix.auth.fixture.AuthTestFixture.TEST_JWT_PROPERTIES;
import static io.fulflix.auth.fixture.AuthTestFixture.USER_DETAILS_RESPONSE;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.fulflix.auth.exception.AuthErrorCode;
import io.fulflix.auth.exception.AuthException;
import io.fulflix.auth.utils.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Application:Authentication")
class AuthenticationServiceTest extends AuthServiceTestHelper {

    @Nested
    @DisplayName("회원 로그인 요청 시")
    class AuthenticationProcessTest {

        @Test
        @DisplayName("존재하지 않는 회원인 경우 예외 발생")
        void throwException_whenUserNotExist() {
            // Given
            given(userAppClient.retrieveUserCredential(anyString())).willThrow(
                RuntimeException.class);

            // When & Then
            assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> authenticationService.authenticate(SIGN_IN_REQUEST));
            verify(userAppClient, times(1)).retrieveUserCredential(anyString());
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않는 경우 예외 발생")
        void throwException_whenPasswordNotMatched() {
            // Given
            given(userAppClient.retrieveUserCredential(anyString())).willReturn(
                USER_DETAILS_RESPONSE);
            given(passwordEncoder.matches(
                SIGN_IN_REQUEST.password(),
                USER_DETAILS_RESPONSE.EncodedPassword())
            ).willReturn(false);

            // When & Then
            assertThatExceptionOfType(AuthException.class)
                .isThrownBy(() -> authenticationService.authenticate(SIGN_IN_REQUEST))
                .extracting(AuthException::getErrorCode)
                .isEqualTo(AuthErrorCode.BAD_CREDENTIALS);

            verify(userAppClient, times(1)).retrieveUserCredential(anyString());
            verify(passwordEncoder, times(1)).matches(
                SIGN_IN_REQUEST.password(),
                USER_DETAILS_RESPONSE.EncodedPassword()
            );
        }

        @Test
        @DisplayName("인증 성공 후 access token 발급")
        void authentication() {
            // Given
            given(userAppClient.retrieveUserCredential(anyString())).willReturn(
                USER_DETAILS_RESPONSE);

            given(passwordEncoder.matches(
                SIGN_IN_REQUEST.password(),
                USER_DETAILS_RESPONSE.EncodedPassword())
            ).willReturn(true);

            given(tokenIssueService.issueAccessToken(TEST_FULFLIX_PRINCIPAL))
                .willReturn(JwtProvider.create(TEST_JWT_PROPERTIES, TEST_FULFLIX_PRINCIPAL));

            // When
            String actual = authenticationService.authenticate(SIGN_IN_REQUEST);

            // Then
            verify(userAppClient, times(1)).retrieveUserCredential(anyString());
            verify(passwordEncoder, times(1)).matches(
                SIGN_IN_REQUEST.password(),
                USER_DETAILS_RESPONSE.EncodedPassword()
            );
        }

    }

}
