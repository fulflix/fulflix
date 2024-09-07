package io.fulflix.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.fulflix.auth.api.AuthTestHelper;
import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.auth.api.dto.UserCreateRequest;
import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.infra.client.UserAppClient;
import io.fulflix.infra.client.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("Application:Authorization")
class AuthorizationServiceTest extends AuthTestHelper {

    @Mock
    private UserAppClient userAppClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthorizationService authorizationService;

    private SignupRequest signupRequest;
    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        signupRequest = MASTER_ADMIN;
        userCreateRequest = UserCreateRequest.of(signupRequest,
            EncodedPassword.from("encoded password"));
        userResponse = fixtureGenerator.giveMeOne(UserResponse.class);
    }

    @Test
    @DisplayName("회원 생성")
    void authorization() {
        // Given
        given(passwordEncoder.encode(signupRequest.password())).willReturn("encoded password");
        given(userAppClient.createUser(userCreateRequest)).willReturn(userResponse);

        // When
        Long authorization = authorizationService.authorization(signupRequest);

        // Then
        assertThat(authorization).isEqualTo(userResponse.id());
        verify(userAppClient, times(1)).createUser(userCreateRequest);
    }

}
