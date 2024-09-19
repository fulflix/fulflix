package io.fulflix.user.api.retrieve;

import static io.fulflix.user.api.retrieve.UserMyPageController.USER_BASE_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.core.app.context.annotation.CurrentUser;
import io.fulflix.core.app.context.annotation.CurrentUserRole;
import io.fulflix.core.web.principal.Role;
import io.fulflix.user.api.retrieve.dto.UserResponse;
import io.fulflix.user.application.UserMyPageService;
import io.fulflix.user.application.UserRetrieveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    public static final String USER_BASE_PATH = "/user";
    private final UserRetrieveService userRetrieveService;
    private final UserMyPageService userMyPageService;

    @GetMapping("/me")
    ResponseEntity<UserResponse> me(
        @CurrentUser Long currentUser,
        @CurrentUserRole Role role
    ) {
        return ResponseEntity.ok(userRetrieveService.loadUserById(currentUser));
    }

    @DeleteMapping("/withdraw")
    ResponseEntity<Void> withdraw(@CurrentUser Long currentUser) {
        userMyPageService.deleteUserById(currentUser);
        return ResponseEntity.noContent().build();
    }

}
