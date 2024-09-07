package io.fulflix.auth.api.dto;

import io.fulflix.auth.domain.Role;

public record SignUpRequest(
    String username,
    String password,
    String name,
    Role type
) {

}
