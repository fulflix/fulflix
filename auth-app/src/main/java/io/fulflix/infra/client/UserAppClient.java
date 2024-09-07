package io.fulflix.infra.client;

import io.fulflix.auth.api.dto.UserCreateRequest;
import io.fulflix.infra.client.dto.UserDetailsResponse;
import io.fulflix.infra.client.dto.UserResponse;

public interface UserAppClient {

    UserResponse createUser(UserCreateRequest userCreateRequest);

    UserDetailsResponse retrieveUser(String username);

}
