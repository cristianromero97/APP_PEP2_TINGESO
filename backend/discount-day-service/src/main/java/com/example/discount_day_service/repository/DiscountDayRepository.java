package com.example.discount_day_service.repository;

import com.example.discount_day_service.entity.DiscountDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiscountDayRepository extends JpaRepository<DiscountDayEntity, Long> {

    List<DiscountDayEntity> findByClientIdAndReservationDate(Long clientId, LocalDate reservationDate);
}