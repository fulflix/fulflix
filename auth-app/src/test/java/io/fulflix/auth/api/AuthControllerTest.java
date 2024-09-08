package io.fulflix.auth.api;

import static io.fulflix.auth.fixture.AuthTestFixture.COMPANY_DELIVERY_MANAGER;
import static io.fulflix.auth.fixture.AuthTestFixture.HUB_ADMIN;
import static io.fulflix.auth.fixture.AuthTestFixture.HUB_COMPANY;
import static io.fulflix.auth.fixture.AuthTestFixture.HUB_DELIVERY_MANAGER;
import static io.fulflix.auth.fixture.AuthTestFixture.MASTER_ADMIN;
import static io.fulflix.auth.fixture.AuthTestFixture.SUPPLY_COMPANY;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.domain.Role;
import io.fulflix.auth.exception.AuthErrorCode;
import io.fulflix.auth.exception.AuthException;
import io.fulflix.common.web.exception.event.ThrowsExceptionEvent;
import io.fulflix.common.web.exception.event.ThrowsExceptionEventListener;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

@DisplayName("API:Auth:sign-in")
class AuthControllerTest extends AuthApiTestHelper {

    private static final String SIGN_UP_URL = "/auth/sign-up";
    private static final String SIGN_IN_URL = "/auth/sign-in";

    @SpyBean
    protected ThrowsExceptionEventListener throwsExceptionEventListener;

    @ParameterizedTest(name = "[{index}][{1}]")
    @MethodSource
    @DisplayName("[회원 가입][POST:201]")
    void signUp(final SignUpRequest signUpRequest, final String description) throws Exception {
        // Given
        given(authorizationService.authorization(signUpRequest)).willReturn(1L);

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_UP_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(signUpRequest))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isCreated());
    }

    private static Stream<Arguments> signUp() {
        return Stream.of(
            Arguments.of(MASTER_ADMIN, Role.MASTER_ADMIN.getDescription()),
            Arguments.of(HUB_ADMIN, Role.HUB_ADMIN.getDescription()),
            Arguments.of(HUB_COMPANY, Role.HUB_COMPANY.getDescription()),
            Arguments.of(SUPPLY_COMPANY, Role.SUPPLY_COMPANY.getDescription()),
            Arguments.of(HUB_DELIVERY_MANAGER, Role.HUB_DELIVERY_MANAGER.getDescription()),
            Arguments.of(COMPANY_DELIVERY_MANAGER, Role.COMPANY_DELIVERY_MANAGER.getDescription())
        );
    }

    @Test
    @DisplayName("[로그인][POST:200]")
    void signIn() throws Exception {
        // Given
        given(authenticationService.authenticate(signInRequest)).willReturn(accessToken);

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_IN_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(signInRequest))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[로그인][POST:403]")
    void throwAuthException() throws Exception {
        // Given
        given(authenticationService.authenticate(signInRequest))
            .willThrow(new AuthException(AuthErrorCode.BAD_CREDENTIALS));

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_IN_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(signInRequest))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isUnauthorized());

        ArgumentCaptor<ThrowsExceptionEvent> eventCaptor =
            ArgumentCaptor.forClass(ThrowsExceptionEvent.class);
        verify(throwsExceptionEventListener)
            .onThrowsExceptionLogging(eventCaptor.capture());
    }

}
