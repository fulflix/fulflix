package io.fulflix.user.api;

import static io.fulflix.common.web.utils.ResponseEntityUtils.created;
import static io.fulflix.user.api.UserController.BASE_USER_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.user.api.dto.UserCreateRequest;
import io.fulflix.user.application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = BASE_USER_PATH,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserController {

    static final String BASE_USER_PATH = "/user";
    static final String USER_DETAILS_URI_FORMAT = "/user/{id}"; // TODO auth-app과의 코드 중복 필요

    private final UserService userService;

    @PostMapping
    ResponseEntity<Void> register(@Valid @RequestBody UserCreateRequest request) {
        return created(USER_DETAILS_URI_FORMAT, userService.createUser(request));
    }

}
