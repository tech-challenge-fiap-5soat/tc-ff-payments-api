package com.tc.ff.payments_api.entrypoint.controller.payload.response;

import static com.tc.ff.payments_api.common.constants.TestsConstants.*;
import static com.tc.ff.payments_api.common.enums.PaymentStatus.PAYMENT_APPROVED;
import static org.junit.jupiter.api.Assertions.*;

import com.tc.ff.payments_api.core.domain.entity.Payment;

public class PaymentResponseTestUtils {

    public static PaymentResponse getPaymentResponse() {
        return new PaymentResponse(RANDOM_ID, RANDOM_ORDER_ID, PAYMENT_APPROVED, RANDOM_VALUE);
    }

    public static PaymentResponse getPaymentResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getOrderId(), payment.getStatus(), payment.getAmount());
    }
}
