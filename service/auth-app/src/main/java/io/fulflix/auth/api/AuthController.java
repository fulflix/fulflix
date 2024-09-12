package io.fulflix.auth.api;

import static io.fulflix.auth.api.AuthController.BASE_AUTH_PATH;
import static io.fulflix.common.web.utils.ResponseEntityUtils.created;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.application.AuthenticationService;
import io.fulflix.auth.application.AuthorizationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = BASE_AUTH_PATH,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AuthController {

    static final String BASE_AUTH_PATH = "/auth";

    private final AuthorizationService authorizationService;
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signupRequest) {
        return created(authorizationService.authorization(signupRequest));
    }

    @PostMapping("/sign-in")
    ResponseEntity<Void> signIn(
        @Valid @RequestBody SignInRequest signInRequest,
        HttpServletResponse response
    ) {
        response.setHeader(AUTHORIZATION, authenticationService.authenticate(signInRequest));
        return ResponseEntity.ok().build();
    }

}
