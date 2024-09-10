package io.fulflix.auth.api.dto;

import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.auth.domain.Role;

// TODO user-app과 클래스 중복 개선 필요
public record CreatePrincipalRequest(
    String username,
    String encodedPassword,
    String name,
    Role type
) {

    public static CreatePrincipalRequest of(
        SignUpRequest signupRequest,
        EncodedPassword encodedPassword
    ) {
        return new CreatePrincipalRequest(
            signupRequest.username(),
            encodedPassword.getValue(),
            signupRequest.name(),
            signupRequest.type()
        );
    }

}
