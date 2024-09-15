package io.fulflix.user.api.retrieve;

import static io.fulflix.user.api.retrieve.UserMyPageController.USER_BASE_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.user.api.retrieve.dto.UserResponse;
import io.fulflix.user.application.UserRetrieveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private final UserRetrieveService userRetrieveService;

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getDetailUser(
        @CurrentUser Long currentUser,
        @CurrentUserRole Role role,
        @PathVariable Long id
    ) {
        log.info("[CurrentUser : {}, {}], [Request : {}]", currentUser, role.name(), id);
        return ResponseEntity.ok(userRetrieveService.loadUserById(id));
    }

    @GetMapping
    ResponseEntity<Page<UserResponse>> getUsers(
        @CurrentUser Long currentUser,
        @CurrentUserRole Role role,
        @PageableDefault Pageable pageable
    ) {
        log.info("[CurrentUser : {}, {}], [Request : {}]", currentUser, role.name(), pageable);
        return ResponseEntity.ok(userRetrieveService.loadAllUsersByPageable(pageable));
    }

}
