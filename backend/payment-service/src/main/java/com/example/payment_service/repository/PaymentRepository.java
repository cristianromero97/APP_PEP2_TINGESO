package com.example.payment_service.repository;

import com.example.payment_service.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByState(String state);

    List<PaymentEntity> findByTotalAmountGreaterThan(double amount);

    PaymentEntity findByReservationId(Long reservationId);

    List<PaymentEntity> findByDiscountId(Long discountId);
}