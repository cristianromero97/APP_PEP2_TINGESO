package com.example.weeklyrack_service.repository;

import com.example.weeklyrack_service.entity.WeeklyRackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WeeklyRackRepository extends JpaRepository<WeeklyRackEntity, Long>{

    List<WeeklyRackEntity> findByDay(String day);

    List<WeeklyRackEntity> findByTimeBlock(String timeBlock);

    List<WeeklyRackEntity> findByDayAndTimeBlock(String day, String timeBlock);

    List<WeeklyRackEntity> findByReservationId(Long reservationId);
}
