package io.fulflix.common.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.customizer.InnerSpec;

public class FixtureCommon {

    public static FixtureMonkey generate() {
        return FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();
    }

    public static InnerSpec setProperty(String propertyName, Object value) {
        return new InnerSpec().property(propertyName, value);
    }

}
