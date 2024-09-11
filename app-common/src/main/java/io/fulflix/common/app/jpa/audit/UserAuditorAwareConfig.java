package io.fulflix.common.app.jpa.audit;

import io.fulflix.common.app.context.UserContextHolder;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class UserAuditorAwareConfig {

    @Bean
    public AuditorAware<Long> getCurrentAuditor() {
        return () -> Optional.ofNullable(UserContextHolder.getCurrentUser())
            .map(Long.class::cast);
    }

}
