package io.fulflix.auth.application;

import static io.fulflix.auth.fixture.AuthTestFixture.SIGN_UP_REQUEST;
import static io.fulflix.auth.fixture.AuthTestFixture.USER_CREATE_REQUEST;
import static io.fulflix.auth.fixture.AuthTestFixture.USER_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    @DisplayName("회원 생성")
    void authorization() {
        // Given
        given(passwordEncoder.encode(SIGN_UP_REQUEST.password())).willReturn("encoded password");
        given(userAppClient.createUser(USER_CREATE_REQUEST)).willReturn(USER_RESPONSE);

        // When
        Long actual = authorizationService.authorization(SIGN_UP_REQUEST);

        // Then
        assertThat(actual).isEqualTo(USER_RESPONSE.id());
        verify(userAppClient, times(1)).createUser(USER_CREATE_REQUEST);
    }

}
