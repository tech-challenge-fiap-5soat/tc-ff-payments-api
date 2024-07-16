package com.tc.ff.payments_api.core.domain.entity;

import static com.tc.ff.payments_api.common.constants.TestsConstants.LOCAL_DATE_NOW;
import static com.tc.ff.payments_api.common.constants.TestsConstants.RANDOM_ID;
import static com.tc.ff.payments_api.common.enums.PaymentStatus.PAYMENT_PENDING;

import com.tc.ff.payments_api.common.constants.TestsConstants;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;

public class PaymentTestUtils {

    public static Payment getPayment() {
        return new Payment(
                RANDOM_ID,
                TestsConstants.RANDOM_ORDER_ID,
                TestsConstants.RANDOM_VALUE,
                PAYMENT_PENDING,
                LOCAL_DATE_NOW,
                null);
    }

    public static Payment getPaymentFromCreateRequest(RegisterPendingPaymentRequest request) {
        Payment payment = new Payment();

        payment.setOrderId(request.orderId());
        payment.setAmount(request.amount());

        return payment;
    }
}
