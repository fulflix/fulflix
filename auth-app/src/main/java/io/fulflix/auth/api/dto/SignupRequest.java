package io.fulflix.auth.api.dto;

import io.fulflix.auth.domain.Role;

public record SignupRequest(
    String username,
    String password,
    String name,
    Role type
) {

}
