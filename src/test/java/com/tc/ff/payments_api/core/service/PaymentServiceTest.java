package com.tc.ff.payments_api.core.service;

import static com.tc.ff.payments_api.core.domain.entity.PaymentTestUtils.getPayment;
import static com.tc.ff.payments_api.core.domain.entity.PaymentTestUtils.getPaymentFromCreateRequest;
import static com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequestTestUtils.getRequestRegisterPendingPayment;
import static com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequestTestUtils.getRequestUpdateRegisteredPaymentApproved;
import static com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequestTestUtils.getRequestUpdateRegisteredPaymentRefused;
import static com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponseTestUtils.getPaymentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tc.ff.payments_api.common.exception.BusinessException;
import com.tc.ff.payments_api.core.domain.entity.Payment;
import com.tc.ff.payments_api.core.domain.entity.PaymentEvent;
import com.tc.ff.payments_api.core.domain.mapper.PaymentMapper;
import com.tc.ff.payments_api.core.service.impl.PaymentServiceImpl;
import com.tc.ff.payments_api.dataprovider.gateway.OrderFeignClient;
import com.tc.ff.payments_api.dataprovider.messaging.PaymentEventPublisher;
import com.tc.ff.payments_api.dataprovider.repository.PaymentRepository;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponse;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentMapper mapper;

    @Mock
    private OrderFeignClient orderClient;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentEventPublisher paymentEventPublisher;

    private static final String MSG_UNABLE_TO_FIND_PAYMENT_BY_FOLLOW_ID = "Unable to find payment by the %s id";

    @Test
    void testCreatePayment() {
        RegisterPendingPaymentRequest request = getRequestRegisterPendingPayment();
        Payment paymentEntity = getPaymentFromCreateRequest(request);
        PaymentResponse response = getPaymentResponse(paymentEntity);

        when(mapper.registerPendingPaymentRequestToPaymentEntity(request)).thenReturn(paymentEntity);
        when(repository.save(paymentEntity)).thenReturn(paymentEntity);
        when(mapper.paymentEntityToPaymentResponse(paymentEntity)).thenReturn(response);

        PaymentResponse result = paymentService.create(request);

        assertEquals(response, result);
        verify(mapper).registerPendingPaymentRequestToPaymentEntity(request);
        verify(repository).save(paymentEntity);
        verify(mapper).paymentEntityToPaymentResponse(paymentEntity);
    }

    @Test
    void testUpdatePayment() {
        String orderId = "orderId";
        UpdateRegisteredPaymentRequest request = getRequestUpdateRegisteredPaymentApproved();
        Payment paymentEntity = getPayment();
        PaymentResponse response = getPaymentResponse(paymentEntity);

        when(repository.findPaymentByOrderId(orderId)).thenReturn(Optional.of(paymentEntity));
        when(repository.save(paymentEntity)).thenReturn(paymentEntity);
        when(mapper.paymentEntityToPaymentResponse(paymentEntity)).thenReturn(response);

        PaymentResponse result = paymentService.update(orderId, request);

        assertEquals(response, result);
        verify(repository).findPaymentByOrderId(orderId);
        verify(repository).save(paymentEntity);
        verify(mapper).paymentEntityToPaymentResponse(paymentEntity);

        ArgumentCaptor<PaymentEvent> eventCaptor = ArgumentCaptor.forClass(PaymentEvent.class);
        verify(paymentEventPublisher).publishMessage(eventCaptor.capture());
        PaymentEvent capturedEvent = eventCaptor.getValue();
        assertNotNull(capturedEvent);
        assertEquals(paymentEntity.getAmount(), capturedEvent.getOrder().getAmount());
        assertEquals("PAYMENT_APPROVED", capturedEvent.getEventType());
    }

    @Test
    void testUpdatePayment_ThrowsBusinessException_WhenPaymentNotFound() {
        String orderId = "orderId";
        UpdateRegisteredPaymentRequest request = getRequestUpdateRegisteredPaymentRefused();

        when(repository.findPaymentByOrderId(orderId)).thenReturn(Optional.empty());

        BusinessException exception =
                assertThrows(BusinessException.class, () -> paymentService.update(orderId, request));
        assertEquals(String.format(MSG_UNABLE_TO_FIND_PAYMENT_BY_FOLLOW_ID, orderId), exception.getMessage());
    }
}
