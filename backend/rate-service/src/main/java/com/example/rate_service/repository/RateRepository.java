package com.example.rate_service.repository;

import com.example.rate_service.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long>{

    List<RateEntity> findByPriceGreaterThan(double price);

    List<RateEntity> findByLapsGreaterThan(int laps);

    List<RateEntity> findByMaximumTimeReservationLessThan(int time);

    //Custom query: Search for rates within a price range
    @Query("SELECT r FROM RateEntity r WHERE r.price BETWEEN :minPrice AND :maxPrice")
    List<RateEntity> findRatesByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    //Custom query to search for rates within a time range (maximumTimeLaps)
    @Query("SELECT r FROM RateEntity r WHERE r.maximumTimeLaps BETWEEN :minTime AND :maxTime")
    List<RateEntity> findRatesByTimeRange(@Param("minTime") int minTime, @Param("maxTime") int maxTime);
}
