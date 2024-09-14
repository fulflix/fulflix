package io.fulflix.gateway.exception;

import io.fulflix.gateway.authenticate.exception.UnAuthorizedException;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@ControllerAdvice
public class GlobalReactiveExceptionHandler {
    @ExceptionHandler(UnAuthorizedException.class)
    public Mono<ResponseEntity<String>> handleUnAuthorizedException(UnAuthorizedException exception, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String id = request.getId();
        String method = request.getMethod().name();
        URI uri = request.getURI();
        HttpStatus status = exception.getStatus();
        String message = exception.getMessage();

        log.error("[{}][{}][{}][{}][{}]", id, method, uri, status, message);

        // 에러 응답 생성
        return Mono.just(ResponseEntity.status(status).body(message));
    }
}
