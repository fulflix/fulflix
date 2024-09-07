package io.fulflix.auth.api;

import static io.fulflix.auth.fixture.AuthTestFixture.COMPANY_DELIVERY_MANAGER;
import static io.fulflix.auth.fixture.AuthTestFixture.HUB_ADMIN;
import static io.fulflix.auth.fixture.AuthTestFixture.HUB_COMPANY;
import static io.fulflix.auth.fixture.AuthTestFixture.HUB_DELIVERY_MANAGER;
import static io.fulflix.auth.fixture.AuthTestFixture.MASTER_ADMIN;
import static io.fulflix.auth.fixture.AuthTestFixture.SUPPLY_COMPANY;
import static io.fulflix.auth.fixture.AuthTestFixture.fixtureGenerator;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.TEST_JWT_PROPERTIES;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.TEST_PRINCIPAL;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.domain.Role;
import io.fulflix.auth.utils.jwt.JwtProvider;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

@DisplayName("API:Auth:sign-in")
class AuthControllerTest extends AuthApiTestHelper {

    private static final String SIGN_UP_URL = "/auth/sign-up";
    private static final String SIGN_IN_URL = "/auth/sign-in";

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
        SignInRequest signInRequest = fixtureGenerator.giveMeOne(SignInRequest.class);
        String accessToken = JwtProvider.create(TEST_JWT_PROPERTIES, TEST_PRINCIPAL);
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

}
