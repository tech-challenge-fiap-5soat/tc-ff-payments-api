package com.tc.ff.payments_api.core.domain.mapper;

import com.tc.ff.payments_api.common.enums.PaymentStatus;
import com.tc.ff.payments_api.core.domain.entity.Payment;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment registerPendingPaymentRequestToPaymentEntity(RegisterPendingPaymentRequest request) {
        if (request == null) {
            return null;
        }

        Payment payment = new Payment();

        payment.setOrderId(request.orderId());
        payment.setAmount(request.amount());

        return payment;
    }

    public PaymentResponse paymentEntityToPaymentResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        Long id = null;
        String orderId = null;
        PaymentStatus status = null;
        BigDecimal amount = null;

        id = payment.getId();
        orderId = payment.getOrderId();
        status = payment.getStatus();
        amount = payment.getAmount();

        PaymentResponse paymentResponse = new PaymentResponse(id, orderId, status, amount);

        return paymentResponse;
    }

    public List<PaymentResponse> entityListToResponseList(List<Payment> payments) {
        if (payments == null) {
            return null;
        }

        List<PaymentResponse> list = new ArrayList<PaymentResponse>(payments.size());
        for (Payment payment : payments) {
            list.add(paymentEntityToPaymentResponse(payment));
        }

        return list;
    }
}
