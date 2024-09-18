package io.fulflix.core.app.context.config;

import io.fulflix.core.app.context.annotation.resolver.CurrentUserArgumentResolver;
import io.fulflix.core.app.context.annotation.resolver.CurrentUserRoleArgumentResolver;
import io.fulflix.core.app.jpa.pageable.FixedPageableHandlerMethodArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver());
        resolvers.add(new CurrentUserRoleArgumentResolver());
        SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();
        resolvers.add(new FixedPageableHandlerMethodArgumentResolver(sortResolver));
    }

}
