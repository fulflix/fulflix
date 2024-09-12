package io.fulflix.common.web.base.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fulflix.common.web.base.TestBase;
import jakarta.annotation.Resource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class WebMvcTestBase extends TestBase {

    @Resource
    protected MockMvc mockMvc;

    @Resource
    protected ObjectMapper objectMapper;

}
