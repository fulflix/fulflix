package io.fulflix.auth;

import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;

import io.fulflix.auth.utils.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
@EnableConfigurationProperties(JwtProperties.class)
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }

}
