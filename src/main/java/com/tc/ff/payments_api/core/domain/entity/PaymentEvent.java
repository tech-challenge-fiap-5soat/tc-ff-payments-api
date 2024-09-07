package com.tc.ff.payments_api.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private String id;
    private String eventType;
    private Order order;
}
