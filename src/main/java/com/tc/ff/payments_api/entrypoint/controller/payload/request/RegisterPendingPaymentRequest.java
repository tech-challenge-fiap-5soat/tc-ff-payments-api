package com.tc.ff.payments_api.entrypoint.controller.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record RegisterPendingPaymentRequest(@NotNull String orderId, @NotNull @Positive BigDecimal amount) {}
