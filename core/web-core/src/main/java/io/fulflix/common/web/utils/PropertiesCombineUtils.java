package io.fulflix.common.web.utils;

public class PropertiesCombineUtils {

    public static final String CONFIGURATION_PROPERTIES_NAME = "spring.config.name";
    public static final String APP_CORE_PROPERTY = "application";
    public static final String BASE_PACKAGE = "io.fulflix";

    private static final String DELIMITER = ", ";

    public static String combinedApplicationProperties(String properties) {
        return combine(APP_CORE_PROPERTY, properties);
    }

    private static String combine(String... properties) {
        return String.join(DELIMITER, properties);
    }

}
