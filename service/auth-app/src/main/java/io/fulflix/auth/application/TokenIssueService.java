package io.fulflix.auth.application;

import io.fulflix.auth.domain.FulflixPrincipal;
import io.fulflix.auth.utils.jwt.JwtProperties;
import io.fulflix.auth.utils.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenIssueService {

    private final JwtProperties jwtProperties;

    public String issueAccessToken(FulflixPrincipal principal) {
        return JwtProvider.create(jwtProperties, principal);
    }

}
