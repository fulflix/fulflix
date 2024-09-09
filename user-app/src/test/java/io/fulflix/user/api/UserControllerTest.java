package io.fulflix.user.api;

import static io.fulflix.common.web.utils.UriComponentUtils.toResourceUri;
import static io.fulflix.fixture.UserFixtures.USER_CREATE_REQUEST;
import static io.fulflix.user.api.UserController.BASE_USER_PATH;
import static io.fulflix.user.api.UserController.USER_DETAILS_URI_FORMAT;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

@DisplayName("API:User")
class UserControllerTest extends UserApiTestHelper {

    @Test
    @DisplayName("[회원 생성][POST:201]")
    void create() throws Exception {
        // Given
        final Long expectedUserId = 1L;
        final URI expectedResourceUri = toResourceUri(USER_DETAILS_URI_FORMAT, expectedUserId);
        given(userService.createUser(USER_CREATE_REQUEST)).willReturn(expectedUserId);

        // When
        ResultActions resultActions = mockMvc.perform(post(BASE_USER_PATH)
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
