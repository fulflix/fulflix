package io.fulflix.user.api.mypage;

import static io.fulflix.common.app.context.interceptor.UserContextHolderInterceptor.X_USER_ID;
import static io.fulflix.common.app.context.interceptor.UserRoleContextHolderInterceptor.X_USER_ROLE;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.common.web.principal.Role;
import io.fulflix.user.api.UserApiTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("API:User:MyPage")
class UserMyPageControllerTest extends UserApiTestHelper {

    private static final HttpHeaders headers = new HttpHeaders();
    private static final String userMyPageUrl = "/user/me";

    static {
        final String userId = "1";
        final String role = Role.MASTER_ADMIN.name();

        headers.add(X_USER_ID, userId);
        headers.add(X_USER_ROLE, role);
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        headers.add(ACCEPT, APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("내 정보 조회")
    void me() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(get(userMyPageUrl)
            .headers(headers)
        );

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk());
    }

}
