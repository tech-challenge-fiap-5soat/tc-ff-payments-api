package com.tc.ff.payments_api.entrypoint.controller.impl;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import com.tc.ff.payments_api.core.service.PaymentService;
import com.tc.ff.payments_api.entrypoint.controller.PaymentController;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<PaymentResponse> create(RegisterPendingPaymentRequest request) {
        return status(CREATED).body(paymentService.create(request));
    }

    @Override
    public ResponseEntity<PaymentResponse> update(String orderId, UpdateRegisteredPaymentRequest request) {
        return status(OK).body(paymentService.update(orderId, request));
    }
}
