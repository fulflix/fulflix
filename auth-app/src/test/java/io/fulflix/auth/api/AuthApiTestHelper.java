package io.fulflix.auth.api;

import io.fulflix.auth.application.AuthenticationService;
import io.fulflix.auth.application.AuthorizationService;
import io.fulflix.common.base.presentation.WebMvcTestBase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = AuthController.class)
public abstract class AuthApiTestHelper extends WebMvcTestBase {

    @MockBean
    protected AuthorizationService authorizationService;

    @MockBean
    protected AuthenticationService authenticationService;

}
