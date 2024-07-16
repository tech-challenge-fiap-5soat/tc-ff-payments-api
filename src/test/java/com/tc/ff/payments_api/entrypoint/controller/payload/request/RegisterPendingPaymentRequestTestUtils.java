package com.tc.ff.payments_api.entrypoint.controller.payload.request;

import static com.tc.ff.payments_api.common.constants.TestsConstants.RANDOM_ORDER_ID;
import static com.tc.ff.payments_api.common.constants.TestsConstants.RANDOM_VALUE;

public class RegisterPendingPaymentRequestTestUtils {

    public static RegisterPendingPaymentRequest getRequestRegisterPendingPayment() {
        return new RegisterPendingPaymentRequest(RANDOM_ORDER_ID, RANDOM_VALUE);
    }
}
