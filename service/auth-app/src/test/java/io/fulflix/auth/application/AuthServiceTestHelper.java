package io.fulflix.auth.application;

import io.fulflix.infra.client.UserAppClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public abstract class AuthServiceTestHelper {

    @Mock
    protected UserAppClient userAppClient;

    @Mock
    protected PasswordEncoder passwordEncoder;

    @Mock
    protected TokenIssueService tokenIssueService;

    @InjectMocks
    protected AuthenticationService authenticationService;

}
