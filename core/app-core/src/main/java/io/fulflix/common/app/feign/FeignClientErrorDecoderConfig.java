package io.fulflix.common.app.feign;

import static org.apache.commons.lang.StringUtils.EMPTY;

import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignClientErrorDecoderConfig {

    @Bean
    public ErrorDecoder decoder() {

        return (methodKey, response) -> {
            Request request = response.request();
            log.error(""" 
                    \s
                        Request Fail: {}
                        URL: {} {}
                        Status: {}
                        Request Header: {}
                        Request Body: {}
                        Response Body: {}
                    \s""",
                methodKey,
                request.httpMethod(),
                request.url(),
                response.status(),
                extractRequestHeader(request),
                extractRequestBody(request),
                extractResponseBody(response)
            );

            return new RuntimeException();
        };
    }

    private static String extractRequestHeader(Request request) {
        Map<String, Collection<String>> headers = request.headers();

        if (Objects.nonNull(headers)) {
            return headers.toString();
        }
        return EMPTY;
    }

    private static String extractRequestBody(Request request) {
        byte[] body = request.body();

        if (Objects.nonNull(body)) {
            new String(body, StandardCharsets.UTF_8);
        }
        return EMPTY;
    }

    private String extractResponseBody(Response response) {
        try (InputStream responseBodyStream = response.body().asInputStream()) {
            return IOUtils.toString(responseBodyStream, String.valueOf(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
