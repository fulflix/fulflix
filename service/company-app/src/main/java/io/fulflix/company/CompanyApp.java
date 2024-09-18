package io.fulflix.company;

import static io.fulflix.core.app.feign.FeignClientConfig.FEIGN_CLIENT_BASE_PACKAGE;
import static io.fulflix.core.web.utils.PropertiesCombineUtils.BASE_PACKAGE;

import io.fulflix.core.web.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@PropertySource(
    value = "classpath:application-company.yml",
    factory = YamlPropertySourceFactory.class
)
@EnableAsync
@EnableFeignClients(basePackages = FEIGN_CLIENT_BASE_PACKAGE)
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class CompanyApp {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApp.class, args);
    }

}
