package io.fulflix.auth.application;

import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.domain.FulflixPrincipal;
import io.fulflix.auth.exception.AuthErrorCode;
import io.fulflix.auth.exception.AuthException;
import io.fulflix.infra.client.UserAppClient;
import io.fulflix.infra.client.dto.UserCredentialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final TokenIssueService tokenIssueService;
    private final UserAppClient userAppClient;

    public String authenticate(SignInRequest signInRequest) {
        FulflixPrincipal principal = verifyUser(signInRequest);
        return tokenIssueService.issueAccessToken(principal);
    }

    // TODO 회원 조회 시 발생하는 예외의 상세한 처리 필요
    private FulflixPrincipal verifyUser(SignInRequest signInRequest) {
        UserCredentialResponse response = userAppClient.retrieveUserCredential(
            signInRequest.username());
        checkPasswordIsMatched(signInRequest, response);

        return FulflixPrincipal.of(
            response.id(),
            response.username(),
            response.role()
        );
    }

    private void checkPasswordIsMatched(
        SignInRequest signInRequest,
        UserCredentialResponse userCredentialResponse
    ) {
        boolean matches = passwordEncoder.matches(
            signInRequest.password(),
            userCredentialResponse.encodedPassword()
        );

        if (!matches) {
            throw new AuthException(AuthErrorCode.BAD_CREDENTIALS);
        }
    }

}
