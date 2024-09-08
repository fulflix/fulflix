package io.fulflix.auth.api;

import static io.fulflix.auth.fixture.AuthTestFixture.fixtureGenerator;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.TEST_JWT_PROPERTIES;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.TEST_PRINCIPAL;

import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.application.AuthenticationService;
import io.fulflix.auth.application.AuthorizationService;
import io.fulflix.auth.utils.jwt.JwtProvider;
import io.fulflix.common.web.base.presentation.WebMvcTestBase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = AuthController.class)
public abstract class AuthApiTestHelper extends WebMvcTestBase {

    @MockBean
    protected AuthorizationService authorizationService;

    @MockBean
    protected AuthenticationService authenticationService;

    protected SignInRequest signInRequest = fixtureGenerator.giveMeOne(SignInRequest.class);
    protected String accessToken = JwtProvider.create(TEST_JWT_PROPERTIES, TEST_PRINCIPAL);

}
