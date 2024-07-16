package com.tc.ff.payments_api.core.service;

import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse create(RegisterPendingPaymentRequest request);

    PaymentResponse update(String orderId, UpdateRegisteredPaymentRequest request);
}
