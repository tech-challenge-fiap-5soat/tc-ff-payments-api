package com.tc.ff.payments_api.entrypoint.controller.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record RegisterPendingPaymentRequest(
        @NotNull @JsonProperty("_id") String orderId, @NotNull @Positive BigDecimal amount) {}
