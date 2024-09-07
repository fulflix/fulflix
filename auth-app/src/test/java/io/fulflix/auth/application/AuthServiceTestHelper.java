package io.fulflix.auth.application;

import io.fulflix.auth.utils.jwt.TokenTestHelper;
import io.fulflix.infra.client.UserAppClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public abstract class AuthServiceTestHelper extends TokenTestHelper {

    @Mock
    protected UserAppClient userAppClient;

    @Mock
    protected PasswordEncoder passwordEncoder;

}
