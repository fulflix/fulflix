package io.fulflix.auth.application;

import static io.fulflix.auth.fixture.AuthTestFixture.fixtureGenerator;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.domain.FulflixPrincipal;
import io.fulflix.auth.exception.AuthErrorCode;
import io.fulflix.auth.exception.AuthException;
import io.fulflix.auth.utils.jwt.JwtProvider;
import io.fulflix.infra.client.dto.UserDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("Application:Authentication")
class AuthenticationServiceTest extends AuthServiceTestHelper {

    @Mock
    private TokenIssueService tokenIssueService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private SignInRequest signInRequest;
    private UserDetailsResponse userDetailsResponse;
    private FulflixPrincipal fulflixPrincipal;

    @BeforeEach
    void setUp() {
        signInRequest = fixtureGenerator.giveMeOne(SignInRequest.class);
        userDetailsResponse = fixtureGenerator.giveMeOne(UserDetailsResponse.class);
        fulflixPrincipal = FulflixPrincipal.of(
            userDetailsResponse.id(),
            userDetailsResponse.username(),
            userDetailsResponse.role()
        );
    }

    @Nested
    @DisplayName("회원 로그인 요청 시")
    class AuthenticationProcessTest {

        @Test
        @DisplayName("존재하지 않는 회원인 경우 예외 발생")
        void throwException_whenUserNotExist() {
            // Given
            given(userAppClient.retrieveUser(anyString())).willThrow(RuntimeException.class);

            // When & Then
            assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> authenticationService.authenticate(signInRequest));
            verify(userAppClient, times(1)).retrieveUser(anyString());
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않는 경우 예외 발생")
        void throwException_whenPasswordNotMatched() {
            // Given
            given(userAppClient.retrieveUser(anyString())).willReturn(userDetailsResponse);
            given(passwordEncoder.matches(
                signInRequest.password(),
                userDetailsResponse.EncodedPassword())
            ).willReturn(false);

            // When & Then
            assertThatExceptionOfType(AuthException.class)
                .isThrownBy(() -> authenticationService.authenticate(signInRequest))
                .extracting(AuthException::getCode)
                .isEqualTo(AuthErrorCode.BAD_CREDENTIALS);

            verify(userAppClient, times(1)).retrieveUser(anyString());
            verify(passwordEncoder, times(1)).matches(
                signInRequest.password(),
                userDetailsResponse.EncodedPassword()
            );
        }

        @Test
        @DisplayName("인증 성공 후 access token 발급")
        void authentication() {
            // Given
            given(userAppClient.retrieveUser(anyString())).willReturn(userDetailsResponse);

            given(passwordEncoder.matches(
                signInRequest.password(),
                userDetailsResponse.EncodedPassword())
            ).willReturn(true);

            given(tokenIssueService.issueAccessToken(fulflixPrincipal))
                .willReturn(JwtProvider.create(TEST_JWT_PROPERTIES, fulflixPrincipal));

            // When
            String actual = authenticationService.authenticate(signInRequest);

            // Then
            verify(userAppClient, times(1)).retrieveUser(anyString());
            verify(passwordEncoder, times(1)).matches(
                signInRequest.password(),
                userDetailsResponse.EncodedPassword()
            );
        }

    }

}
