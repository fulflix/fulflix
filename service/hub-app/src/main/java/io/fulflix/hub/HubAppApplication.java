package io.fulflix.hub;

import static io.fulflix.common.app.feign.FeignClientConfig.FEIGN_CLIENT_BASE_PACKAGE;
import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;

import io.fulflix.common.web.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
    @PropertySource(
        name = "hub-app-properties",
        value = "classpath:application-hub.yml",
        factory = YamlPropertySourceFactory.class
    )
})
@EnableFeignClients//(basePackages = FEIGN_CLIENT_BASE_PACKAGE)
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class HubAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubAppApplication.class, args);
    }

}
