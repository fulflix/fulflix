package io.fulflix.auth.api;

import static io.fulflix.auth.fixture.AuthTestFixture.ACCESS_TOKEN;
import static io.fulflix.auth.fixture.AuthTestFixture.SIGN_IN_REQUEST;
import static io.fulflix.auth.fixture.AuthTestFixture.SIGN_UP_REQUEST;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.exception.AuthErrorCode;
import io.fulflix.auth.exception.AuthException;
import io.fulflix.common.web.exception.event.ThrowsExceptionEvent;
import io.fulflix.common.web.exception.event.ThrowsExceptionEventListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("[회원 가입][POST:201]")
    void signUp() throws Exception {
        // Given
        given(authorizationService.authorization(SIGN_UP_REQUEST)).willReturn(1L);

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_UP_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(SIGN_UP_REQUEST))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isCreated());
    }
    
    @Test
    @DisplayName("[회원 가입][POST:400]")
    void badRequest_signUp() throws Exception {
        // Given
        SignUpRequest signUpRequest = new SignUpRequest("", "", "", null);

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_UP_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(signUpRequest))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[로그인][POST:200]")
    void signIn() throws Exception {
        // Given
        given(authenticationService.authenticate(SIGN_IN_REQUEST)).willReturn(ACCESS_TOKEN);

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_IN_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(SIGN_IN_REQUEST))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[로그인][POST:401]")
    void badRequest_signIn() throws Exception {
        // Given
        SignInRequest signInRequest = new SignInRequest("", "");

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_IN_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(signInRequest))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[로그인][POST:403]")
    void throwAuthException() throws Exception {
        // Given
        given(authenticationService.authenticate(SIGN_IN_REQUEST))
            .willThrow(new AuthException(AuthErrorCode.BAD_CREDENTIALS));

        // When
        ResultActions resultActions = mockMvc.perform(post(SIGN_IN_URL)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(SIGN_IN_REQUEST))
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
