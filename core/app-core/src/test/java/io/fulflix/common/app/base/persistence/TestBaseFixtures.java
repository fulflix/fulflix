package io.fulflix.common.app.base.persistence;

import static io.fulflix.common.app.context.interceptor.UserContextHolderInterceptor.X_USER_ID;
import static io.fulflix.common.app.context.interceptor.UserRoleContextHolderInterceptor.X_USER_ROLE;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.fulflix.common.web.principal.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestBaseFixtures {

    public static final String userId = "1";
    public static final String role = Role.MASTER_ADMIN.name();
    public static final String CREATED_AT = "created_at";
    public static final Sort SORT = Sort.by(Direction.DESC, CREATED_AT);
    public static final PageRequest PAGE_REQUEST = PageRequest.of(1, 2, SORT);

    public static final HttpHeaders MOCK_HEADERS = new HttpHeaders();
    public static final MultiValueMap<String, String> PAGE_REQUEST_MAP = new LinkedMultiValueMap<>();

    static {
        MOCK_HEADERS.add(X_USER_ID, userId);
        MOCK_HEADERS.add(X_USER_ROLE, role);
        MOCK_HEADERS.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        MOCK_HEADERS.add(ACCEPT, APPLICATION_JSON_VALUE);

        PAGE_REQUEST_MAP.add("page", String.valueOf(PAGE_REQUEST.getPageNumber()));
        PAGE_REQUEST_MAP.add("size", String.valueOf(PAGE_REQUEST.getPageSize()));
        PAGE_REQUEST_MAP.add("sort", SORT.toString());
    }
}
