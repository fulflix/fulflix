package io.fulflix.core.web.utils;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public class UriComponentUtils {

    public static <T> URI toResourceUri(final String baseUri, T path) {
        return UriComponentsBuilder.fromUriString(baseUri)
            .buildAndExpand(path)
            .toUri();
    }

}
