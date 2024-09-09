package io.fulflix.auth.api.dto;

import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.auth.domain.Role;

// TODO user-app과 클래스 중복 개선 필요
public record UserCreateRequest(
    String username,
    String encodedPassword,
    String name,
    Role type
) {

    public static UserCreateRequest of(
        SignUpRequest signupRequest,
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
