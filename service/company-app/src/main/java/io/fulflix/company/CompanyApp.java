package io.fulflix.company;

import io.fulflix.common.web.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;

import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;

@PropertySources({
        @PropertySource(
                name = "company-app-properties",
                value = "classpath:application-company.yml",
                factory = YamlPropertySourceFactory.class
        )
})
@EnableAsync
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class CompanyApp {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApp.class, args);
    }

}
