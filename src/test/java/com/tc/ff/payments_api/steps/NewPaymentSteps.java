package com.tc.ff.payments_api.steps;

import static com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequestTestUtils.getRequestRegisterPendingPayment;
import static com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponseTestUtils.getPaymentResponse;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tc.ff.payments_api.core.domain.entity.Payment;
import com.tc.ff.payments_api.core.domain.entity.PaymentTestUtils;
import com.tc.ff.payments_api.core.domain.mapper.PaymentMapper;
import com.tc.ff.payments_api.core.service.PaymentService;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RequiredArgsConstructor
public class NewPaymentSteps {

    @Inject
    private MockMvc mockMvc;

    private RegisterPendingPaymentRequest requestBody;
    private PaymentResponse expectedResponse;
    private Payment payment;

    @Inject
    @Mock
    private PaymentService service;

    @Inject
    @Mock
    private PaymentMapper mapper;

    private ResultActions result;

    @Given("there is a valid {string}")
    public void there_is_a_valid(String type) {
        requestBody = getRequestRegisterPendingPayment();
        payment = PaymentTestUtils.getPaymentFromCreateRequest(requestBody);
        expectedResponse = getPaymentResponse();
    }

    @When("I send a POST request to {string} with the request body")
    public void i_send_a_post_request_to_with_the_request_body(String string) throws Exception {
        when(mapper.registerPendingPaymentRequestToPaymentEntity(requestBody)).thenReturn(payment);
        when(service.create(requestBody)).thenReturn(expectedResponse);

        result = mockMvc.perform(
                        post(string).contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestBody)))
                .andExpect(status().isCreated());
    }

    @Then("the response should have a status of 201")
    public void theResponseShouldHaveAStatusOf201Created() throws Exception {
        result.andExpect(status().isCreated());
    }

    @Then("the response body should match the expected {string}")
    public void theResponseBodyShouldMatchTheExpectedType(String type) throws Exception {
        if ("PaymentResponse".equals(type)) {
            result.andExpect(jsonPath("$.id", is(expectedResponse.id())));
            result.andExpect(jsonPath("$.orderId", is(expectedResponse.orderId())));
            result.andExpect(jsonPath("$.status", is(expectedResponse.status().name())));
        }
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
