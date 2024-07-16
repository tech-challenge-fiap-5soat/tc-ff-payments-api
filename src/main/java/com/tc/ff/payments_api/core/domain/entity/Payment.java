package com.tc.ff.payments_api.core.domain.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.TemporalType.TIMESTAMP;

import com.tc.ff.payments_api.common.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime updatedAt;
}
