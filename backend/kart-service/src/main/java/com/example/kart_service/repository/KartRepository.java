package com.example.kart_service.repository;

import com.example.kart_service.entity.KartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface KartRepository extends JpaRepository<KartEntity, Long>{

    List<KartEntity> findByAvailable(String available); // Filtro por Disponibilidad

    //Custom query: Search for karts with model
    @Query("SELECT k FROM KartEntity k WHERE k.model = :model")
    KartEntity findByModel(@Param("model") String model);
}
