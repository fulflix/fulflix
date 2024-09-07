package io.fulflix.auth.application;

import static io.fulflix.auth.domain.Role.COMPANY_DELIVERY_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.infra.client.UserAppClient;
import io.fulflix.infra.client.dto.UserResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Application:Authorization")
class AuthorizationServiceTest {

    @Mock
    private UserAppClient userAppClient;

    @InjectMocks
    private AuthorizationService authorizationService;

    private SignupRequest signupRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest(
            "testUser",
            "testPassword",
            "testName",
            COMPANY_DELIVERY_MANAGER
        );
        userResponse = new UserResponse(
            1L,
            "testUser",
            "testName",
            COMPANY_DELIVERY_MANAGER.getDescription(),
            LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("회원 생성")
    void authorization() {
        // Given
        given(userAppClient.createUser(signupRequest))
            .willReturn(userResponse);

        // When
        Long authorization = authorizationService.authorization(signupRequest);

        // Then
        assertThat(authorization).isEqualTo(userResponse.id());
        verify(userAppClient, times(1)).createUser(signupRequest);
    }

}
