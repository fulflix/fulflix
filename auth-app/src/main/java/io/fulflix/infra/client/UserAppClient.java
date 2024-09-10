package io.fulflix.infra.client;

import static io.fulflix.infra.client.UserAppClient.USER_APP_CLIENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.fulflix.auth.api.dto.CreatePrincipalRequest;
import io.fulflix.infra.client.dto.UserCredentialResponse;
import io.fulflix.infra.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(USER_APP_CLIENT)
public interface UserAppClient {

    String USER_APP_CLIENT = "user-app";
    String CREATE_PRINCIPAL_URI = "/principal";
    String RETRIEVE_USER_CREDENTIAL_URI = "/credential/{username}";

    @PostMapping(
        path = CREATE_PRINCIPAL_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    UserResponse createPrincipal(@RequestBody CreatePrincipalRequest createPrincipalRequest);

    @GetMapping(
        path = RETRIEVE_USER_CREDENTIAL_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    UserCredentialResponse retrieveUserCredential(@PathVariable String username);

}
