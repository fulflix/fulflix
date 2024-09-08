package io.fulflix.common.web.exception.event;

import io.fulflix.common.web.exception.GlobalErrorResponse;
import lombok.Getter;

@Getter
public final class ThrowsExceptionEvent extends StackTraceExtractor {

    private final Exception exception;
    private final GlobalErrorResponse response;

    private ThrowsExceptionEvent(
        Exception exception,
        GlobalErrorResponse response
    ) {
        super(exception);
        this.exception = exception;
        this.response = response;
    }

    public static ThrowsExceptionEvent of(
        Exception exception,
        GlobalErrorResponse response
    ) {
        return new ThrowsExceptionEvent(exception, response);
    }

}
