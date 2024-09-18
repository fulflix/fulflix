package io.fulflix.gateway.exception;

import io.fulflix.gateway.authenticate.exception.UnAuthorizedException;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GlobalReactiveExceptionHandler {

    @Bean
    @Order(-2)
    public ErrorWebExceptionHandler globalErrorWebExceptionHandler() {
        return (exchange, ex) -> {
            if (ex instanceof UnAuthorizedException unAuthorizedException) {
                HttpStatus status = unAuthorizedException.getStatus();
                String message = unAuthorizedException.getMessage();

                exchange.getResponse().setStatusCode(status);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                DataBuffer dataBuffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(message.getBytes());

                logging(exchange.getRequest(), status, message);

                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            }
            return Mono.error(ex);
        };
    }

    private void logging(ServerHttpRequest request, HttpStatus status, String message) {
        String id = request.getId();
        String method = request.getMethod().name();
        URI uri = request.getURI();

        log.error("[{}][{}][{}][{}][{}]", id, method, uri, status, message);
    }

}
