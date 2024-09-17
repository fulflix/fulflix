package io.fulflix.infra.client.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private LocalDateTime createdAt;
}
