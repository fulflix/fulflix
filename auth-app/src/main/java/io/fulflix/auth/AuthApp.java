package io.fulflix.auth;

import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;
import static io.fulflix.common.web.utils.PropertiesCombineUtils.CONFIGURATION_PROPERTIES_NAME;
import static io.fulflix.common.web.utils.PropertiesCombineUtils.combinedApplicationProperties;

import io.fulflix.auth.utils.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
@EnableConfigurationProperties(JwtProperties.class)
public class AuthApp {

    private static final String AUTH_APP_PROPERTY = "application-auth";

    public static void main(String[] args) {
        System.setProperty(
            CONFIGURATION_PROPERTIES_NAME,
            combinedApplicationProperties(AUTH_APP_PROPERTY)
        );
        SpringApplication.run(AuthApp.class, args);
    }

}
