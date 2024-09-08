package io.fulflix.common.web.base;

import static io.fulflix.common.web.base.TestBase.TEST;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@ActiveProfiles(TEST)
@TestConstructor(autowireMode = ALL)
public abstract class TestBase {

    static final String TEST = "test";

}
