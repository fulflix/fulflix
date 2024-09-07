package io.fulflix.auth.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.auth.application.AuthorizationService;
import io.fulflix.auth.domain.Role;
import io.fulflix.common.base.presentation.WebMvcTestBase;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

@WebMvcTest(controllers = AuthController.class)
@DisplayName("API:Auth:sign-in")
class AuthControllerTest extends WebMvcTestBase {

    private static final String SIGN_UP_URL = "/auth/sign-up";

    @MockBean
    private AuthorizationService authorizationService;

    @ParameterizedTest(name = "[{index}][{1}]")
    @MethodSource
    @DisplayName("[회원 가입][POST:201]")
    void signUp(final SignupRequest signUpRequest, final String description) throws Exception {
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
            Arguments.of(new SignupRequest(
                "master-admin",
                "password",
                "이부장",
                Role.MASTER_ADMIN
            ), Role.MASTER_ADMIN.getDescription()),
            Arguments.of(new SignupRequest(
                "hub-admin",
                "password",
                "장차장",
                Role.HUB_ADMIN
            ), Role.HUB_ADMIN.getDescription()),
            Arguments.of(new SignupRequest(
                "hub-company",
                "password",
                "최과장",
                Role.HUB_COMPANY
            ), Role.HUB_COMPANY.getDescription()),
            Arguments.of(new SignupRequest(
                "supply-company",
                "password",
                "유과장",
                Role.SUPPLY_COMPANY
            ), Role.SUPPLY_COMPANY.getDescription()),
            Arguments.of(new SignupRequest(
                "hub-delivery-manager",
                "password",
                "김대리",
                Role.HUB_DELIVERY_MANAGER
            ), Role.MASTER_ADMIN.getDescription()),
            Arguments.of(new SignupRequest(
                "company-delivery-manager",
                "password",
                "강대리",
                Role.COMPANY_DELIVERY_MANAGER
            ), Role.COMPANY_DELIVERY_MANAGER.getDescription())
        );
    }

}
