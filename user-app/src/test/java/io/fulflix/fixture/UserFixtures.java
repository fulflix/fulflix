package io.fulflix.fixture;

import io.fulflix.user.api.dto.UserCreateRequest;
import io.fulflix.user.repo.model.Role;

public class UserFixtures {

    public static final String USERNAME = "hong-gd";
    public static final String NAME = "홍길동";

    public static final UserCreateRequest USER_CREATE_REQUEST = new UserCreateRequest(
        USERNAME,
        "endcoded password",
        NAME,
        Role.MASTER_ADMIN
    );

}
