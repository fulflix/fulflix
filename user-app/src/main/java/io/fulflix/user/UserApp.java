package io.fulflix.user;

import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;
import static io.fulflix.common.web.utils.PropertiesCombineUtils.CONFIGURATION_PROPERTIES_NAME;
import static io.fulflix.common.web.utils.PropertiesCombineUtils.combinedApplicationProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class UserApp {

    private static final String USER_APP_PROPERTY = "application-user";

    public static void main(String[] args) {
        System.setProperty(
            CONFIGURATION_PROPERTIES_NAME,
            combinedApplicationProperties(USER_APP_PROPERTY)
        );
        SpringApplication.run(UserApp.class, args);
    }

}
