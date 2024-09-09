package io.fulflix.user.api.authenticate;

import static io.fulflix.common.web.utils.ResponseEntityUtils.created;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.user.api.authenticate.dto.UserAuthorityCreateRequest;
import io.fulflix.user.api.authenticate.dto.UserCredentialResponse;
import io.fulflix.user.application.UserAuthenticateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserAuthenticateController {

    // TODO auth-app과의 코드 중복 개선 필요
    static final String GET_AN_USER_CREDENTIAL_URI_FORMAT = "/credential/{username}";

    private final UserAuthenticateUseCase userAuthenticateUseCase;

    @PostMapping("/principal")
    ResponseEntity<Void> create(@Valid @RequestBody UserAuthorityCreateRequest request) {
        return created(
            GET_AN_USER_CREDENTIAL_URI_FORMAT,
            userAuthenticateUseCase.createUser(request)
        );
    }

    @GetMapping("/credential/{username}")
    ResponseEntity<UserCredentialResponse> retrieveUserCredential(@PathVariable String username) {
        return ResponseEntity.ok(userAuthenticateUseCase.loadUserCredentialByUsername(username));
    }

}
