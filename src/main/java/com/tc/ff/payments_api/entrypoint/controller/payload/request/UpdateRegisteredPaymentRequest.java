package com.tc.ff.payments_api.entrypoint.controller.payload.request;

import com.tc.ff.payments_api.common.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateRegisteredPaymentRequest(@NotNull PaymentStatus status) {}
