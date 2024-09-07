package io.fulflix.common.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UserContextConfig implements WebMvcConfigurer {

    public static final String ALL_REQUEST = "/**";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextHolderInterceptor())
            .addPathPatterns(ALL_REQUEST);
    }

}
