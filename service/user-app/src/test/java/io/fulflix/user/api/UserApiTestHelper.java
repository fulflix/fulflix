package io.fulflix.user.api;

import io.fulflix.core.web.base.presentation.WebMvcTestBase;
import io.fulflix.user.api.authenticate.UserAuthenticateController;
import io.fulflix.user.api.retrieve.UserMyPageController;
import io.fulflix.user.api.retrieve.UserRetrieveController;
import io.fulflix.user.application.UserAuthenticateUseCase;
import io.fulflix.user.application.UserRetrieveService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {
    UserAuthenticateController.class,
    UserMyPageController.class,
    UserRetrieveController.class
})
public class UserApiTestHelper extends WebMvcTestBase {


    @MockBean
    protected UserAuthenticateUseCase userAuthenticateUseCase;

    @MockBean
    protected UserRetrieveService userRetrieveService;

}
