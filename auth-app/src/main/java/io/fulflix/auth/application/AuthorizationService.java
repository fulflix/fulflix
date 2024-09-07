package io.fulflix.auth.application;

import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.auth.api.dto.UserCreateRequest;
import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.infra.client.UserAppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserAppClient userAppClient;
    private final PasswordEncoder passwordEncoder;

    public Long authorization(SignupRequest signupRequest) {
        UserCreateRequest userCreateRequest = encodePassword(signupRequest);
        return userAppClient.createUser(userCreateRequest).id();
    }

    private UserCreateRequest encodePassword(SignupRequest signupRequest) {
        EncodedPassword encodedPassword = EncodedPassword.from(
            passwordEncoder.encode(signupRequest.password())
        );
        return UserCreateRequest.of(signupRequest, encodedPassword);
    }

}