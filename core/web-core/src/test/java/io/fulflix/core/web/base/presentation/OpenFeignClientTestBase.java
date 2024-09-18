package io.fulflix.core.web.base.presentation;

import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@AutoConfigureWireMock(port = 0)
public abstract class OpenFeignClientTestBase extends WebMvcTestBase {

    @Resource
    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer.stop();
        wireMockServer.start();
    }

    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
    }

}
