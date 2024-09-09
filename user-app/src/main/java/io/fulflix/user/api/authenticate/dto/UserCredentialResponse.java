package io.fulflix.user.api.authenticate.dto;

import io.fulflix.user.repo.model.Role;
import io.fulflix.user.repo.model.User;

public record UserCredentialResponse(
    Long userId,
    String username,
    String encodedPassword,
    Role role
) {

    public static UserCredentialResponse from(User user) {
        return new UserCredentialResponse(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getRole()
        );
    }

}
