package io.fulflix.user.api;

import static io.fulflix.common.app.context.interceptor.UserContextHolderInterceptor.X_USER_ID;
import static io.fulflix.common.app.context.interceptor.UserRoleContextHolderInterceptor.X_USER_ROLE;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.common.web.base.presentation.WebMvcTestBase;
import io.fulflix.common.web.principal.Role;
import io.fulflix.user.api.authenticate.UserAuthenticateController;
import io.fulflix.user.api.retrieve.UserMyPageController;
import io.fulflix.user.api.retrieve.UserRetrieveController;
import io.fulflix.user.application.UserAuthenticateUseCase;
import io.fulflix.user.application.UserMyPageService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

@WebMvcTest(controllers = {
    UserAuthenticateController.class,
    UserMyPageController.class,
    UserRetrieveController.class
})
public class UserApiTestHelper extends WebMvcTestBase {


    @MockBean
    protected UserAuthenticateUseCase userAuthenticateUseCase;

    @MockBean
    protected UserMyPageService userMyPageService;

}
