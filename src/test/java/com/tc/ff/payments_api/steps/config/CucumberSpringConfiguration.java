package com.tc.ff.payments_api.steps.config;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

import com.tc.ff.payments_api.core.domain.mapper.PaymentMapper;
import com.tc.ff.payments_api.core.service.PaymentService;
import com.tc.ff.payments_api.dataprovider.repository.PaymentRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {Object.class})
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.tc.ff.payments_api.steps")
public class CucumberSpringConfiguration {

    @MockBean
    private PaymentRepository repository;

    @Mock
    private PaymentService service;

    @MockBean
    private PaymentMapper mapper;
}
