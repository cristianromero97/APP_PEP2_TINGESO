package com.example.discount_visit_service.repository;

import com.example.discount_visit_service.entity.DiscountVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountVisitRepository extends JpaRepository<DiscountVisitEntity, Long> {
}
