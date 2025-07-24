package com.example.discount_service.repository;

import com.example.discount_service.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
    List<DiscountEntity> findByMinPerson(int minPerson);

    List<DiscountEntity> findByMaxPerson(int maxPerson);

    List<DiscountEntity> findByPercentage(double percentage);

    List<DiscountEntity> findByPercentageGreaterThan(double percentage);

    List<DiscountEntity> findByPercentageLessThan(double percentage);

    List<DiscountEntity> findByPercentageBetween(double minPercentage, double maxPercentage);

    //Custom query: Search for discount within a range people with max and min person
    @Query("SELECT d FROM DiscountEntity d WHERE :min BETWEEN d.minPerson AND d.maxPerson AND :max BETWEEN d.minPerson AND d.maxPerson")
    List<DiscountEntity> findDiscountsByPersonRange(@Param("min") int min, @Param("max") int max);

    //Custom query: Search for discount within a range people with "amount people" attribute
    @Query("SELECT d FROM DiscountEntity d WHERE :amountPeople BETWEEN d.minPerson AND d.maxPerson")
    DiscountEntity findByPersonRange(@Param("amountPeople") int amountPeople);

}
