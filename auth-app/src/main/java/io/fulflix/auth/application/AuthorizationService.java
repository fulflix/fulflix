package io.fulflix.auth.application;

import feign.Response;
import io.fulflix.auth.api.dto.CreatePrincipalRequest;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.infra.client.UserAppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserAppClient userAppClient;
    private final PasswordEncoder passwordEncoder;

    // TODO Kafka를 이용한 회원 생성 Event 발행
    public String authorization(SignUpRequest signupRequest) {
        CreatePrincipalRequest createPrincipalRequest = encodePassword(signupRequest);
        Response response = userAppClient.createPrincipal(createPrincipalRequest);
        return extractHeaderLocation(response);
    }

    private String extractHeaderLocation(Response response) {
        return response.headers()
            .get(HttpHeaders.LOCATION)
            .stream()
            .findFirst()
            .orElseThrow();
    }

    private CreatePrincipalRequest encodePassword(SignUpRequest signupRequest) {
        EncodedPassword encodedPassword = EncodedPassword.from(
            passwordEncoder.encode(signupRequest.password())
        );
        return CreatePrincipalRequest.of(signupRequest, encodedPassword);
    }

}
