package com.example.client_service.repository;

import com.example.client_service.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    public ClientEntity findByRut(String rut);

    List<ClientEntity> findByName(String name);

    Optional<ClientEntity> findByEmail(String email);

    List<ClientEntity> findByMonthlyVisits(int monthlyVisits);

    List<ClientEntity> findByBirthDate(LocalDate birth_date);
}
