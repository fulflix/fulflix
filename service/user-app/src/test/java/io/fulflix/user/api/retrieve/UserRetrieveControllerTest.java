package io.fulflix.user.api.retrieve;

import static io.fulflix.fixture.UserFixtures.USER_RESPONSE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.user.api.UserApiTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("API:User:Retrieve")
class UserRetrieveControllerTest extends UserApiTestHelper {

    private static final String RETRIEVE_PATH = "/user/{id}";

    @Test
    @DisplayName("회원 상세 조회")
    void retrieveUser() throws Exception {
        // Given
        final Long userId = 2L;
        given(userMyPageService.loadUserById(userId)).willReturn(USER_RESPONSE);

        // When
        ResultActions resultActions = mockMvc.perform(get(RETRIEVE_PATH, userId)
            .headers(MOCK_HEADERS)
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk());
    }

}
