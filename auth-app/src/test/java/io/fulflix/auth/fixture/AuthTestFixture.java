package io.fulflix.auth.fixture;

import static io.fulflix.auth.domain.Role.MASTER_ADMIN;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.AUDIENCE;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.EXPIRATION_MINUTES;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.ID;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.ISSUER;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.ROLES;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.TEST_PLAIN_SECRET_KEY;
import static io.fulflix.auth.utils.jwt.TokenTestHelper.USERNAME;

import io.fulflix.auth.api.dto.CreatePrincipalRequest;
import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.auth.domain.FulflixPrincipal;
import io.fulflix.auth.utils.jwt.JwtProperties;
import io.fulflix.auth.utils.jwt.JwtProvider;
import io.fulflix.infra.client.dto.UserDetailsResponse;
import io.fulflix.infra.client.dto.UserResponse;
import java.time.LocalDateTime;

public abstract class AuthTestFixture {

    private static final String NAME = "홍길동";
    public static final String PASSWORD = "password";
    public static final String ENCODED_PASSWORD = "encoded password";

    public static FulflixPrincipal TEST_FULFLIX_PRINCIPAL = new FulflixPrincipal(ID, USERNAME,
        ROLES);
    public static JwtProperties TEST_JWT_PROPERTIES = new JwtProperties(
        ISSUER,
        AUDIENCE,
        EXPIRATION_MINUTES,
        TEST_PLAIN_SECRET_KEY
    );
    public static String ACCESS_TOKEN = JwtProvider.create(TEST_JWT_PROPERTIES,
        TEST_FULFLIX_PRINCIPAL);

    public static SignUpRequest SIGN_UP_REQUEST = new SignUpRequest(
        USERNAME,
        PASSWORD,
        NAME,
        MASTER_ADMIN
    );
    public static CreatePrincipalRequest USER_CREATE_REQUEST = CreatePrincipalRequest.of(
        SIGN_UP_REQUEST,
        EncodedPassword.from(ENCODED_PASSWORD)
    );
    public static UserResponse USER_RESPONSE = new UserResponse(
        1L,
        USERNAME,
        NAME,
        MASTER_ADMIN.getDescription(),
        LocalDateTime.now()
    );

    public static SignInRequest SIGN_IN_REQUEST = new SignInRequest(USERNAME, PASSWORD);
    public static UserDetailsResponse USER_DETAILS_RESPONSE = new UserDetailsResponse(
        1L,
        USERNAME,
        ENCODED_PASSWORD,
        MASTER_ADMIN,
        LocalDateTime.now()
    );

}
