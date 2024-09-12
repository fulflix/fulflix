package io.fulflix.auth.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(
    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 3, max = 20, message = "아이디는 3자 이상 20자 이하로 입력하세요.")
    String username,

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상 입력하세요.")
    String password
) {

}
