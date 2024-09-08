package io.fulflix.common.web.utils;

import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {

    public static ResponseEntity<Void> created(String uriFormat, Object path) {
        return ResponseEntity.created(
                UriComponentUtils.toResourceUri(
                    uriFormat,
                    path
                ))
            .build();
    }

}
