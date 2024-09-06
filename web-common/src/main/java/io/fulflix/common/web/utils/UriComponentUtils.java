package io.fulflix.common.web.utils;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public class UriComponentUtils {

    public static <T> URI generateResourceUri(final String baseUri, T path) {
        return UriComponentsBuilder.fromUriString(baseUri)
            .buildAndExpand(path)
            .toUri();
    }

}
