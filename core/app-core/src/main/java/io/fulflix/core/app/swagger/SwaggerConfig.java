package io.fulflix.core.app.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Fulflix API",
        description = "For fulfillment system"
    )
)
@Configuration
public class SwaggerConfig {

}
