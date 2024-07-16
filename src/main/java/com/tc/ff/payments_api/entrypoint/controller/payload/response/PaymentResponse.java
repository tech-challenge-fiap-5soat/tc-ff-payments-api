package com.tc.ff.payments_api.entrypoint.controller.payload.response;

import com.tc.ff.payments_api.common.enums.PaymentStatus;
import java.math.BigDecimal;

public record PaymentResponse(Long id, String orderId, PaymentStatus status, BigDecimal amount) {}
