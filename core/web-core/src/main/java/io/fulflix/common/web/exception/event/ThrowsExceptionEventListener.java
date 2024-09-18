package io.fulflix.common.web.exception.event;

import io.fulflix.common.web.exception.response.GlobalErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ThrowsExceptionEventListener {

    @Async
    @EventListener
    public void onThrowsExceptionLogging(ThrowsExceptionEvent throwsExceptionEvent) {
        Exception exception = throwsExceptionEvent.getException();
        GlobalErrorResponse response = throwsExceptionEvent.getResponse();

        log.error("""
                             \s
                             REQUEST :[{}, {} {}]
                             RESPONSE :[{}, {}]
                             EXCEPTION :[{}, {}]
                             TRACE :[{}]
                \s""",
            response.getMessage(),
            response.getMethod(),
            response.getPath(),
            response.getCode(),
            response.getMessage(),
            exception.getClass().getName(),
            exception.getMessage(),
            throwsExceptionEvent.formatStackTrace()
        );

        // TODO notify throws exception
    }

}
