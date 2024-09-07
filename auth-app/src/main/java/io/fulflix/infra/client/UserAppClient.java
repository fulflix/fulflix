package io.fulflix.infra.client;

import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.infra.client.dto.UserResponse;

public interface UserAppClient {

    UserResponse createUser(SignupRequest signupRequest);

}
