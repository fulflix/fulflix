package io.fulflix.infra.client.dto;

import io.fulflix.auth.domain.Role;
import java.time.LocalDateTime;

public record UserCredentialResponse(
    Long id,
    String username,
    String encodedPassword,
    Role role,
    LocalDateTime createdAt
) {

}
