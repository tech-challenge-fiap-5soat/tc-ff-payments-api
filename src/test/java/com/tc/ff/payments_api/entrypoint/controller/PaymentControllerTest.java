package com.tc.ff.payments_api.entrypoint.controller;

import static com.tc.ff.payments_api.common.constants.PathConstants.PAYMENTS;
import static com.tc.ff.payments_api.common.constants.TestsConstants.*;
import static com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequestTestUtils.getRequestRegisterPendingPayment;
import static com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequestTestUtils.getRequestUpdateRegisteredPaymentApproved;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tc.ff.payments_api.common.config.TestSecutiryConfig;
import com.tc.ff.payments_api.core.service.PaymentService;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import(TestSecutiryConfig.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests of PaymentControllerController")
class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void testCreatePayment() throws Exception {
        var request = getRequestRegisterPendingPayment();
        var requestBodyStr = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post(PAYMENTS)
                        .content(requestBodyStr)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(paymentService).create(request);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsToCreateBadRequestTests")
    void shouldDoNotCallServiceWhenCreatePaymentAndReturnBadRequestStatus(String requestBody) throws Exception {
        mvc.perform(post(PAYMENTS)
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(paymentService, never()).create(any(RegisterPendingPaymentRequest.class));
    }

    private static Stream<Arguments> provideArgumentsToCreateBadRequestTests() {
        return Stream.of(
                Arguments.of(
                        """
                                {
                                    "orderId": random-order-id,
                                    "amount": -1
                                }
                                """),
                Arguments.of(
                        """
                                {
                                    "orderId": random-order-id,
                                    "amount": 0
                                }
                                """));
    }

    @Test
    void testUpdatePayment() throws Exception {
        var orderId = RANDOM_ORDER_ID;
        var request = getRequestUpdateRegisteredPaymentApproved();
        var requestBodyStr = new ObjectMapper().writeValueAsString(request);

        mvc.perform(put(PAYMENTS + "/" + orderId)
                        .content(requestBodyStr)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(paymentService).update(orderId, request);
    }
}
