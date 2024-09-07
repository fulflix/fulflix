package io.fulflix.auth.api;

import static io.fulflix.auth.api.AuthController.BASE_AUTH_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.auth.api.dto.SignInRequest;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.application.AuthenticationService;
import io.fulflix.auth.application.AuthorizationService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(
    path = BASE_AUTH_PATH,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AuthController {

    static final String BASE_AUTH_PATH = "/auth";
    private static final String USER_DETAILS_URI_FORMAT = "/user/{id}";

    private final AuthorizationService authorizationService;

    @PostMapping("/sign-up")
    ResponseEntity<Void> signUp(@RequestBody SignUpRequest signupRequest) {
        URI userDetailsUri = UriComponentsBuilder.fromUriString(USER_DETAILS_URI_FORMAT)
            .buildAndExpand(authorizationService.authorization(signupRequest))
            .toUri();

        return ResponseEntity.created(userDetailsUri)
            .build();
    }

}
