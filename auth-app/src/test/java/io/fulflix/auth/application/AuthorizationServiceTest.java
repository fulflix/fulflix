package io.fulflix.auth.application;

import static io.fulflix.auth.fixture.AuthTestFixture.SIGN_UP_REQUEST;
import static io.fulflix.auth.fixture.AuthTestFixture.USER_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.LOCATION;

import feign.Request;
import feign.Request.Body;
import feign.Request.HttpMethod;
import feign.RequestTemplate;
import feign.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("Application:Authorization")
public class AuthorizationServiceTest extends AuthServiceTestHelper {

    public static final String GET_USER_CREDENTIAL_URI = "/credential/1";
    
    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private Response response;

    @Mock
    private RequestTemplate restTemplate;

    @BeforeEach
    void setUp() throws Exception {
        response = setUpMockResponse();
    }

    @Test
    @DisplayName("회원 생성")
    void authorization() {
        // Given
        given(passwordEncoder.encode(SIGN_UP_REQUEST.password())).willReturn("encoded password");
        given(userAppClient.createPrincipal(USER_CREATE_REQUEST)).willReturn(response);

        // When
        String actual = authorizationService.authorization(SIGN_UP_REQUEST);

        // Then
        assertThat(actual).isEqualTo(GET_USER_CREDENTIAL_URI);
        verify(userAppClient, times(1)).createPrincipal(USER_CREATE_REQUEST);
    }

    private Response setUpMockResponse() throws Exception {
        Map<String, Collection<String>> mockHeaders = new HashMap<>();
        Collection<String> locations = new ArrayList<>();
        locations.add(GET_USER_CREDENTIAL_URI);
        mockHeaders.put(LOCATION, locations);

        byte[] bytes = new ObjectMapper().writeValueAsBytes("mock request body");
        Body body = Body.create(bytes);

        Request request = Request.create(
            HttpMethod.POST,
            "/principal",
            mockHeaders,
            body,
            restTemplate
        );

        return Response.builder()
            .request(request)
            .headers(mockHeaders)
            .build();
    }

}
