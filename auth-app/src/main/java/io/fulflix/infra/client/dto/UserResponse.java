package io.fulflix.infra.client.dto;

import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String username,
    String name,
    String roles,
    LocalDateTime createdAt
) {

}
