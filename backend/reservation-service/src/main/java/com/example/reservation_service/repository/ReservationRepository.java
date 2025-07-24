package com.example.reservation_service.repository;

import com.example.reservation_service.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByDate(Date date);

    List<ReservationEntity> findByClientId(Long clientId);

    List<ReservationEntity> findByRateId(Long rateId);

    List<ReservationEntity> findByAmountPeopleGreaterThan(int amountPeople);

    //Custom query: Search for reservations within a date range
    @Query("SELECT r FROM ReservationEntity r WHERE r.date BETWEEN :startDate AND :endDate")
    List<ReservationEntity> findReservationsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
