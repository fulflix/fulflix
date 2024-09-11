package io.fulflix.user.api.mypage;

import static io.fulflix.user.api.mypage.UserMyPageController.USER_BASE_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.user.api.mypage.dto.UserResponse;
import io.fulflix.user.application.UserMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = USER_BASE_PATH,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserMyPageController {

    static final String USER_BASE_PATH = "/user";
    private final UserMyPageService userMyPageService;

    @GetMapping("/me")
    ResponseEntity<UserResponse> me(
        @CurrentUser Long currentUser,
        @CurrentUserRole Role role
    ) {
        return ResponseEntity.ok(userMyPageService.loadUserById(currentUser));
    }

}
