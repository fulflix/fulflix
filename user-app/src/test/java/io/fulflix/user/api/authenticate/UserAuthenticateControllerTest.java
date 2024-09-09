package io.fulflix.user.api.authenticate;

import static io.fulflix.common.web.utils.UriComponentUtils.toResourceUri;
import static io.fulflix.fixture.UserFixtures.USER_CREATE_REQUEST;
import static io.fulflix.user.api.authenticate.UserAuthenticateController.GET_AN_USER_CREDENTIAL_URI_FORMAT;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.user.api.UserApiTestHelper;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

@DisplayName("API:User")
class UserAuthenticateControllerTest extends UserApiTestHelper {

    @Test
    @DisplayName("[회원 생성][POST:201]")
    void create() throws Exception {
        // Given
        final String principalUri = "/principal";
        final Long expectedUserId = 1L;
        final URI expectedResourceUri = toResourceUri(
            GET_AN_USER_CREDENTIAL_URI_FORMAT,
            expectedUserId
        );
        given(userAuthenticateUseCase.createUser(USER_CREATE_REQUEST)).willReturn(expectedUserId);

        // When
        ResultActions resultActions = mockMvc.perform(post(principalUri)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(USER_CREATE_REQUEST))
        );

        // Then
        resultActions.andDo(print())
            .andExpect(header().stringValues(
                HttpHeaders.LOCATION, expectedResourceUri.toString()
            ))
            .andExpect(status().isCreated());
    }

}
