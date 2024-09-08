package com.tc.ff.payments_api.core.service.impl;

import static com.tc.ff.payments_api.common.enums.PaymentStatus.*;

import com.tc.ff.payments_api.common.enums.PaymentStatus;
import com.tc.ff.payments_api.common.exception.BusinessException;
import com.tc.ff.payments_api.core.domain.entity.Order;
import com.tc.ff.payments_api.core.domain.entity.Payment;
import com.tc.ff.payments_api.core.domain.entity.PaymentEvent;
import com.tc.ff.payments_api.core.domain.mapper.PaymentMapper;
import com.tc.ff.payments_api.core.service.PaymentService;
import com.tc.ff.payments_api.dataprovider.messaging.PaymentEventPublisher;
import com.tc.ff.payments_api.dataprovider.repository.PaymentRepository;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final PaymentEventPublisher paymentEventPublisher;

    private static final String MSG_UNABLE_TO_FIND_PAYMENT_BY_FOLLOW_ID = "Unable to find payment by the %s id";

    @Override
    public PaymentResponse create(RegisterPendingPaymentRequest request) {
        var payment = mapper.registerPendingPaymentRequestToPaymentEntity(request);

        payment.setStatus(PAYMENT_PENDING);

        var ret = payment;
        try {
            ret = repository.save(payment);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.PAYMENT_ERROR);
        }
        publishPaymentEvent(payment);
        return mapper.paymentEntityToPaymentResponse(ret);
    }

    @Override
    public PaymentResponse update(String orderId, UpdateRegisteredPaymentRequest request) {
        Payment payment = repository
                .findPaymentByOrderId(orderId)
                .orElseThrow(
                        () -> new BusinessException(String.format(MSG_UNABLE_TO_FIND_PAYMENT_BY_FOLLOW_ID, orderId)));

        payment.setStatus(request.status());
        var ret = payment;
        try {
            ret = repository.save(payment);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.PAYMENT_ERROR);
        }
        publishPaymentEvent(payment);

        return mapper.paymentEntityToPaymentResponse(ret);
    }

    private void publishPaymentEvent(Payment payment) {
        var orderEvent = Order.builder()
                .id(payment.getOrderId())
                .amount(payment.getAmount())
                .build();

        var paymentEvent = PaymentEvent.builder()
                .id(UUID.randomUUID().toString())
                .eventType(payment.getStatus().name())
                .order(orderEvent)
                .build();

        var result = paymentEventPublisher.publishMessage(paymentEvent);
    }
}