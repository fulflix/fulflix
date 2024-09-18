package io.fulflix.infra.client.user;

import static io.fulflix.infra.client.user.UserClient.USER_APP_CLIENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.fulflix.core.app.feign.FeignClientErrorDecoder;
import io.fulflix.core.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = USER_APP_CLIENT, configuration = {
    FulflixPrincipalRequestHeaderInterceptor.class,
    FeignClientErrorDecoder.class
})
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
