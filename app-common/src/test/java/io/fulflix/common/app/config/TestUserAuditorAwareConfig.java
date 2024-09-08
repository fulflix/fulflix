package io.fulflix.common.app.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class TestUserAuditorAwareConfig {

    @Bean
    public AuditorAware<Long> getCurrentAuditor() {
        return () -> Optional.of(1L);
    }

}
