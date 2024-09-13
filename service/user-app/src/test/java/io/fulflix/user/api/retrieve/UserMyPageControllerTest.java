package io.fulflix.user.api.retrieve;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.user.api.UserApiTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("API:User:MyPage")
class UserMyPageControllerTest extends UserApiTestHelper {

    private static final String MY_PAGE_URL = "/user/me";

    @Test
    @DisplayName("내 정보 조회")
    void me() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(get(MY_PAGE_URL)
            .headers(MOCK_HEADERS)
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk());
    }

}
