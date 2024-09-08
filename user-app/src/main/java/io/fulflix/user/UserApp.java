package io.fulflix.user;

import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;

import io.fulflix.common.web.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
    @PropertySource(
        name = "user-app-properties",
        value = "classpath:application-user.yml",
        factory = YamlPropertySourceFactory.class
    )
})
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }

}
