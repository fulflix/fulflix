package io.fulflix.user.api.authenticate.dto;

import io.fulflix.common.web.principal.Role;
import io.fulflix.user.repo.model.User;
import java.time.LocalDateTime;

public record UserCredentialResponse(
    Long id,
    String username,
    String encodedPassword,
    Role role,
    LocalDateTime createdAt
) {

    public static UserCredentialResponse from(User user) {
        return new UserCredentialResponse(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getRole(),
            user.getCreatedAt()
        );
    }

}
