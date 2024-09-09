package io.fulflix.common.web.utils;

import java.net.URI;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {

    public static ResponseEntity<Void> created(String uriFormat, Object path) {
        return ResponseEntity.created(toResourceUri(uriFormat, path))
            .build();
    }

    private static URI toResourceUri(String uriFormat, Object path) {
        return UriComponentUtils.toResourceUri(uriFormat, path);
    }

}
