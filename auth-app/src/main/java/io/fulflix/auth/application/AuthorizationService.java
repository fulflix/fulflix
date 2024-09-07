package io.fulflix.auth.application;

import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.infra.client.UserAppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private UserAppClient userAppClient;

    public Long authorization(SignupRequest signupRequest) {
        return userAppClient.createUser(signupRequest).id();
    }

}
