package io.fulflix.user.api.retrieve;

import static io.fulflix.user.api.retrieve.UserMyPageController.USER_BASE_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.user.api.retrieve.dto.UserResponse;
import io.fulflix.user.application.UserMyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(
    path = USER_BASE_PATH,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserRetrieveController {

    private final UserMyPageService userMyPageService;

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getDetailUser(
        @PathVariable Long id,
        @CurrentUser Long currentUser,
        @CurrentUserRole Role role
    ) {
        log.info("[CurrentUser : {}, {}], [Request : {}]", currentUser, role.name(), id);
        return ResponseEntity.ok(userMyPageService.loadUserById(id));
    }
}
