package io.fulflix.user.api.authenticate.dto;

import io.fulflix.core.web.principal.Role;
import io.fulflix.user.repo.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// TODO auth-app과 클래스 중복 개선 필요
public record CreatePrincipalRequest(
    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 3, max = 20, message = "아이디는 3자 이상 20자 이하로 입력하세요.")
    String username,

    @NotBlank(message = "비밀번호를 입력하세요.")
    String encodedPassword,

    @NotBlank(message = "이름을 입력하세요.")
    String name,

    @NotNull(message = "회원 타입을 선택하세요.")
    Role type
) {

    public User toEntity() {
        return User.of(username, encodedPassword, name, type);
    }

}
