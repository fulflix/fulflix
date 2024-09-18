package io.fulflix.core.web.config;

import jakarta.annotation.Nullable;
import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    public static final String YAML_PROPERTIES_LOAD_ERROR_FORMAT = "Could not load YAML properties from :[%s]";

    @Override
    public PropertySource<?> createPropertySource(
        @Nullable String name,
        EncodedResource resource
    ) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        Properties properties = factory.getObject();
        String filename = resource.getResource().getFilename();

        if (properties == null) {
            throw new IOException(YAML_PROPERTIES_LOAD_ERROR_FORMAT.formatted(filename));
        }
        return new PropertiesPropertySource(filename, properties);
    }

}
