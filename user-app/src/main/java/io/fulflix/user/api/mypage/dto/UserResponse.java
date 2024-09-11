package io.fulflix.user.api.mypage.dto;

import io.fulflix.user.repo.model.User;
import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String username,
    String name,
    LocalDateTime createdAt
) {

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getCreatedAt()
        );
    }

}
