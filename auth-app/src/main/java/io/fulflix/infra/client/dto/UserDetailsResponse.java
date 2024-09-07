package io.fulflix.infra.client.dto;

import io.fulflix.auth.domain.Role;
import java.time.LocalDateTime;

public record UserDetailsResponse(
    Long id,
    String username,
    String EncodedPassword,
    Role role,
    LocalDateTime createdAt
) {

}
