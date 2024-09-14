package io.fulflix.delivery;

import io.fulflix.common.web.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static io.fulflix.common.web.utils.PropertiesCombineUtils.BASE_PACKAGE;

@EnableJpaAuditing(modifyOnCreate = false)
@PropertySources({
    @PropertySource(
            name = "delivery-app-properties",
            value = "classpath:application-delivery.yml",
            factory = YamlPropertySourceFactory.class
    )
})
//@EnableFeignClients(basePackages = FEIGN_CLIENT_BASE_PACKAGE)
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class DeliveryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryAppApplication.class, args);
    }

}
