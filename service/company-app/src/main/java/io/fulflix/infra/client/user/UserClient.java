package io.fulflix.infra.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static io.fulflix.infra.client.user.UserClient.USER_APP_CLIENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(USER_APP_CLIENT)
public interface UserClient extends UserService {
    String USER_APP_CLIENT = "user-app";
    String USER_BY_ID_URI = "/user/{id}";

    @GetMapping(
            path = USER_BY_ID_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    UserResponse getUserById(@PathVariable("id") Long userId);
}