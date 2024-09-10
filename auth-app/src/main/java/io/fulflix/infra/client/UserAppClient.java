package io.fulflix.infra.client;

import io.fulflix.auth.api.dto.CreatePrincipalRequest;
import io.fulflix.infra.client.dto.UserDetailsResponse;
import io.fulflix.infra.client.dto.UserResponse;

public interface UserAppClient {

    UserResponse createPrincipal(CreatePrincipalRequest createPrincipalRequest);

    UserDetailsResponse retrieveUser(String username);

}
