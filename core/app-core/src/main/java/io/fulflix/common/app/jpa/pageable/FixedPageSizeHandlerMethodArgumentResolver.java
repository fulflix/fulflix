package io.fulflix.common.app.jpa.pageable;

import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FixedPageSizeHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private static final int FIXED_PAGE_SIZE = 10;
    private static final List<Integer> SUPPORTED_PAGE_SIZES = List.of(10, 30, 50);

    public FixedPageSizeHandlerMethodArgumentResolver(SortHandlerMethodArgumentResolver sortResolver) {
        super(sortResolver);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        int reAdjustedPageSize = adjustPageSize(pageable);

        return PageRequest.of(pageable.getPageNumber(), reAdjustedPageSize, pageable.getSort());
    }

    private int adjustPageSize(Pageable pageable) {
        if (isNotSupportedPageSize(pageable)) {
            return FIXED_PAGE_SIZE;
        }
        return pageable.getPageSize();
    }

    private boolean isNotSupportedPageSize(Pageable pageable) {
        return !SUPPORTED_PAGE_SIZES.contains(pageable.getPageSize());
    }

}
