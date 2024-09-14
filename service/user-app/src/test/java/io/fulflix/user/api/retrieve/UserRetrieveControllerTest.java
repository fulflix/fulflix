package io.fulflix.user.api.retrieve;

import static io.fulflix.common.app.base.persistence.TestBaseFixtures.MOCK_HEADERS;
import static io.fulflix.common.app.base.persistence.TestBaseFixtures.PAGE_REQUEST_MAP;
import static io.fulflix.fixture.UserFixtures.PAGE_USER_RESPONSE;
import static io.fulflix.fixture.UserFixtures.USER_RESPONSE;
import static io.fulflix.user.api.retrieve.UserMyPageController.USER_BASE_PATH;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.fulflix.user.api.UserApiTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
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

    @Test
    @DisplayName("회원 목록 조회")
    void retrieveUser2() throws Exception {
        // Given
        given(userMyPageService.loadAllUsersByPageable(any(Pageable.class)))
            .willReturn(PAGE_USER_RESPONSE);

        // When
        ResultActions resultActions = mockMvc.perform(get(USER_BASE_PATH)
            .headers(MOCK_HEADERS)
            .params(PAGE_REQUEST_MAP)
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].username", is(USER_RESPONSE.username())));
    }

}
