package io.fulflix.core.app.jpa.audit;

import io.fulflix.core.app.context.holder.UserContextHolder;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class UserAuditorAwareConfig {

    @Bean
    public AuditorAware<Long> getCurrentAuditor() {
        return () -> Optional.of(UserContextHolder.getCurrentUser())
            .map(Long.class::cast);
    }

}
