package io.fulflix.user.api;

import io.fulflix.common.web.base.presentation.WebMvcTestBase;
import io.fulflix.user.api.authenticate.UserAuthenticateController;
import io.fulflix.user.application.UserAuthenticateUseCase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = UserAuthenticateController.class)
public class UserApiTestHelper extends WebMvcTestBase {

    @MockBean
    protected UserAuthenticateUseCase userAuthenticateUseCase;

}
