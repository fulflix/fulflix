package io.fulflix.fixture;

import io.fulflix.common.web.principal.Role;
import io.fulflix.user.api.authenticate.dto.CreatePrincipalRequest;
import io.fulflix.user.api.authenticate.dto.UserCredentialResponse;
import io.fulflix.user.api.retrieve.dto.UserResponse;
import java.time.LocalDateTime;

public class UserFixtures {

    public static final Long USER_ID = 1L;
    public static final String USERNAME = "hong-gd";
    public static final String NAME = "홍길동";
    public static final String ENCODED_PASSWORD = "endcoded password";
    public static final Role MASTER_ADMIN_ROLE = Role.MASTER_ADMIN;

    public static final CreatePrincipalRequest CREATE_PRINCIPAL_REQUEST = new CreatePrincipalRequest(
        USERNAME,
        ENCODED_PASSWORD,
        NAME,
        MASTER_ADMIN_ROLE
    );

    public static final UserCredentialResponse USER_CREDENTIAL_RESPONSE
        = new UserCredentialResponse(
            USER_ID,
            USERNAME,
            ENCODED_PASSWORD,
            MASTER_ADMIN_ROLE,
            LocalDateTime.now()
        );

    public static final UserResponse USER_RESPONSE
        = new UserResponse(
            USER_ID,
            USERNAME,
            NAME,
            LocalDateTime.now()
        );

}
