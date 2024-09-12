package io.fulflix.hub;

import io.fulflix.common.web.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static io.fulflix.common.app.feign.FeignClientConfig.FEIGN_CLIENT_BASE_PACKAGE;

@EnableJpaAuditing(modifyOnCreate = false)
@PropertySources({
		@PropertySource(
				name = "hub-app-properties",
				value = "classpath:application-hub.yml",
				factory = YamlPropertySourceFactory.class
		)
})
@EnableFeignClients(basePackages = FEIGN_CLIENT_BASE_PACKAGE)
@SpringBootApplication
public class HubAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubAppApplication.class, args);
	}

}
