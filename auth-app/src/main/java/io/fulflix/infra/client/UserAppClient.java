package io.fulflix.infra.client;

import io.fulflix.auth.api.dto.CreatePrincipalRequest;
import io.fulflix.infra.client.dto.UserCredentialResponse;
import io.fulflix.infra.client.dto.UserResponse;

public interface UserAppClient {

    UserResponse createPrincipal(CreatePrincipalRequest createPrincipalRequest);

    UserCredentialResponse retrieveUserCredential(String username);

}
