package io.fulflix.auth.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import io.fulflix.auth.api.dto.CreatePrincipalRequest;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.domain.EncodedPassword;
import io.fulflix.core.web.exception.BusinessException;
import io.fulflix.core.web.exception.response.GlobalErrorResponse;
import io.fulflix.infra.client.UserAppClient;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserAppClient userAppClient;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    // TODO Kafka를 이용한 회원 생성 Event 발행
    public String authorization(SignUpRequest signupRequest) {
        CreatePrincipalRequest createPrincipalRequest = encodePassword(signupRequest);
        return createPrincipal(createPrincipalRequest);
    }

    private String createPrincipal(CreatePrincipalRequest createPrincipalRequest) {
        Response response = userAppClient.createPrincipal(createPrincipalRequest);
        handleError(response);
        return extractHeaderLocation(response);
    }

    private void handleError(Response response) {
        if (response.status() != HttpStatus.CREATED.value()) {
            GlobalErrorResponse errorResponse = extractError(response);

            throw new BusinessException(
                HttpStatus.valueOf(response.status()),
                errorResponse.getCode(),
                errorResponse.getMessage()
            );
        }
    }

    private GlobalErrorResponse extractError(Response response) {
        try (InputStream responseBodyStream = response.body().asInputStream()) {
            String body = StreamUtils.copyToString(responseBodyStream, StandardCharsets.UTF_8);
            return objectMapper.readValue(body, GlobalErrorResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
