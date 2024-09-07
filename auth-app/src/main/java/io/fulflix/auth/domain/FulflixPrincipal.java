package io.fulflix.auth.domain;

public record FulflixPrincipal(
    Long id,
    String username,
    String roles
) {

    public static FulflixPrincipal of(Long id, String email, String roles) {
        return new FulflixPrincipal(id, email, roles);
    }

}
