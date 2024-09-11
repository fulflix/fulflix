package io.fulflix.common.app.context.config;

import io.fulflix.common.app.context.annotation.resolver.CurrentUserArgumentResolver;
import io.fulflix.common.app.context.annotation.resolver.CurrentUserRoleArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CurrentUserResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver());
        resolvers.add(new CurrentUserRoleArgumentResolver());
    }

}