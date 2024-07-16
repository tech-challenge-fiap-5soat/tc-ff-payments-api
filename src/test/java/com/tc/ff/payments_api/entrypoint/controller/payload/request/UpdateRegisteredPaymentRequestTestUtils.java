package com.tc.ff.payments_api.entrypoint.controller.payload.request;

import static com.tc.ff.payments_api.common.enums.PaymentStatus.PAYMENT_APPROVED;
import static com.tc.ff.payments_api.common.enums.PaymentStatus.PAYMENT_REFUSED;

import com.tc.ff.payments_api.common.enums.PaymentStatus;

public class UpdateRegisteredPaymentRequestTestUtils {

    private static UpdateRegisteredPaymentRequest getRequestUpdateRegisteredPayment(PaymentStatus status) {
        return new UpdateRegisteredPaymentRequest(status);
    }

    public static UpdateRegisteredPaymentRequest getRequestUpdateRegisteredPaymentApproved() {
        return getRequestUpdateRegisteredPayment(PAYMENT_APPROVED);
    }

    public static UpdateRegisteredPaymentRequest getRequestUpdateRegisteredPaymentRefused() {
        return getRequestUpdateRegisteredPayment(PAYMENT_REFUSED);
    }
}
