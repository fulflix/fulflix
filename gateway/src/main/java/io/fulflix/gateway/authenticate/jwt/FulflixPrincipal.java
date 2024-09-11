package io.fulflix.gateway.authenticate.jwt;

public record FulflixPrincipal(
    Long id,
    String username,
    Role roles
) {

    public static FulflixPrincipal of(Long id, String email, Role roles) {
        return new FulflixPrincipal(id, email, roles);
    }

}
