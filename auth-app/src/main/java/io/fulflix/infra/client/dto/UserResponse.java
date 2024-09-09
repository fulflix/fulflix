package io.fulflix.infra.client.dto;

import java.time.LocalDateTime;

// TODO user-app과 클래스 중복 개선 필요
public record UserResponse(
    Long id,
    String username,
    String name,
    String roles,
    LocalDateTime createdAt
) {

}
