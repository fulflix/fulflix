package io.fulflix.auth.fixture;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import io.fulflix.auth.api.dto.SignUpRequest;
import io.fulflix.auth.domain.Role;
import io.fulflix.common.fixture.FixtureCommon;

public abstract class AuthTestFixture {

    public static final FixtureMonkey fixtureGenerator = FixtureCommon.generate();
    public static ArbitraryBuilder<SignUpRequest> signupRequestBuilder = fixtureGenerator
        .giveMeBuilder(SignUpRequest.class);

    public static SignUpRequest MASTER_ADMIN = signupRequestBuilder
        .setLazy("type", () -> Role.MASTER_ADMIN).sample();
    public static SignUpRequest HUB_ADMIN = signupRequestBuilder
        .setLazy("type", () -> Role.HUB_ADMIN).sample();
    public static SignUpRequest HUB_COMPANY = signupRequestBuilder
        .setLazy("type", () -> Role.HUB_COMPANY).sample();
    public static SignUpRequest SUPPLY_COMPANY = signupRequestBuilder
        .setLazy("type", () -> Role.SUPPLY_COMPANY).sample();
    public static SignUpRequest HUB_DELIVERY_MANAGER = signupRequestBuilder
        .setLazy("type", () -> Role.HUB_DELIVERY_MANAGER).sample();
    public static SignUpRequest COMPANY_DELIVERY_MANAGER = signupRequestBuilder
        .setLazy("type", () -> Role.COMPANY_DELIVERY_MANAGER).sample();

}
