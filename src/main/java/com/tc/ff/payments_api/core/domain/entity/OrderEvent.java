package com.tc.ff.payments_api.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String id;
    private String eventType;
    private String orderStatus;
    private Order order;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        @JsonProperty("_id")
        private String id;

        private Customer customer;
        private String orderStatus;
        private List<OrderItem> orderItems;
        private String createdAt;
        private String updatedAt;
        private BigDecimal amount;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private String name;
        private String email;
        private String cpf;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        private Product product;
        private int quantity;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        @JsonProperty("_id")
        private String id;

        private String name;
        private BigDecimal price;
        private String category;
    }
}
