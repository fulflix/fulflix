package io.fulflix.auth.api.dto;

public record SignInRequest(
    String username,
    String password
) {

}
