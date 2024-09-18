package io.fulflix.core.app.jpa.pageable;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FixedPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private static final int FIXED_START_PAGE_NUMBER = 1;
    private static final int FIXED_PAGE_SIZE = 10;
    private static final List<Integer> SUPPORTED_PAGE_SIZES = List.of(10, 30, 50);

    public FixedPageableHandlerMethodArgumentResolver(SortHandlerMethodArgumentResolver sortResolver) {
        super(sortResolver);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        int reAdjustedStartPageNumber = adjustStartPageNumber(pageable);
        int reAdjustedPageSize = adjustPageSize(pageable);

        return PageRequest.of(reAdjustedStartPageNumber, reAdjustedPageSize, pageable.getSort());
    }

    private int adjustStartPageNumber(Pageable pageable) {
        if (isStartPageOverThanZero(pageable)) {
            return pageable.getPageNumber() - FIXED_START_PAGE_NUMBER;
        }
        return ZERO;
    }

    private boolean isStartPageOverThanZero(Pageable pageable) {
        return pageable.getPageNumber() > 0;
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
