package io.fulflix.auth.api;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import io.fulflix.auth.api.dto.SignupRequest;
import io.fulflix.auth.domain.Role;
import io.fulflix.common.base.presentation.WebMvcTestBase;
import io.fulflix.common.fixture.FixtureCommon;

public abstract class AuthTestHelper extends WebMvcTestBase {

    protected static final FixtureMonkey fixtureGenerator = FixtureCommon.generate();
    protected static ArbitraryBuilder<SignupRequest> signupRequestBuilder = fixtureGenerator
        .giveMeBuilder(SignupRequest.class);

    protected static SignupRequest MASTER_ADMIN = signupRequestBuilder
        .setLazy("type", () -> Role.MASTER_ADMIN).sample();
    protected static SignupRequest HUB_ADMIN = signupRequestBuilder
        .setLazy("type", () -> Role.HUB_ADMIN).sample();
    protected static SignupRequest HUB_COMPANY = signupRequestBuilder
        .setLazy("type", () -> Role.HUB_COMPANY).sample();
    protected static SignupRequest SUPPLY_COMPANY = signupRequestBuilder
        .setLazy("type", () -> Role.SUPPLY_COMPANY).sample();
    protected static SignupRequest HUB_DELIVERY_MANAGER = signupRequestBuilder
        .setLazy("type", () -> Role.HUB_DELIVERY_MANAGER).sample();
    protected static SignupRequest COMPANY_DELIVERY_MANAGER = signupRequestBuilder
        .setLazy("type", () -> Role.COMPANY_DELIVERY_MANAGER).sample();

}
