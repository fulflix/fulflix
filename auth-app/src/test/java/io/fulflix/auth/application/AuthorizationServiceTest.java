package io.fulflix.auth.application;

import static io.fulflix.auth.fixture.AuthTestFixture.MASTER_ADMIN;
import static io.fulflix.auth.fixture.AuthTestFixture.fixtureGenerator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.auth.api.dto.UserCreateRequest;
import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.infra.client.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Application:Authorization")
class AuthorizationServiceTest extends AuthServiceTestHelper {

    @InjectMocks
    private AuthorizationService authorizationService;

    private SignupRequest signupRequest;
    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        signupRequest = MASTER_ADMIN;
        userCreateRequest = UserCreateRequest.of(
            signupRequest,
            EncodedPassword.from("encoded password")
        );
        userResponse = fixtureGenerator.giveMeOne(UserResponse.class);
    }

    @Test
    @DisplayName("회원 생성")
    void authorization() {
        // Given
        given(passwordEncoder.encode(signupRequest.password())).willReturn("encoded password");
        given(userAppClient.createUser(userCreateRequest)).willReturn(userResponse);

        // When
        Long actual = authorizationService.authorization(signupRequest);

        // Then
        assertThat(actual).isEqualTo(userResponse.id());
        verify(userAppClient, times(1)).createUser(userCreateRequest);
    }

}
