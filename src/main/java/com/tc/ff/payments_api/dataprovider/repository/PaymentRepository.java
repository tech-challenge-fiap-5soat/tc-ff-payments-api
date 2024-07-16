package com.tc.ff.payments_api.dataprovider.repository;

import com.tc.ff.payments_api.core.domain.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findPaymentByOrderId(String orderId);
}
