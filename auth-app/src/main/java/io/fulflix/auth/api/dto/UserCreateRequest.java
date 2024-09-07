package io.fulflix.auth.api.dto;

import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.auth.domain.Role;

public record UserCreateRequest(
    String username,
    String encodedPassword,
    String name,
    Role type
) {

    public static UserCreateRequest of(
        SignupRequest signupRequest,
        EncodedPassword encodedPassword
    ) {
        return new UserCreateRequest(
            signupRequest.username(),
            encodedPassword.getValue(),
            signupRequest.name(),
            signupRequest.type()
        );
    }

}
