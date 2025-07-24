package com.example.kart_service.service;

import com.example.kart_service.entity.KartEntity;
import com.example.kart_service.repository.KartRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;

    //Service for obtaining karts
    public List<KartEntity> getKarts() {
        return kartRepository.findAll();
    }

    //Service for obtaining karts by ID
    public Optional<KartEntity> getKartById(@NotNull Long id) {
        return kartRepository.findById(id);
    }

    //Service for obtaining karts by Available
    public List<KartEntity> getAvailableKarts() {
        return kartRepository.findByAvailable("Disponible");
    }

    //Service for obtaining karts by Unavailable
    public List<KartEntity> getUnavailableKarts() {
        return kartRepository.findByAvailable("No Disponible");
    }

    //Service for obtaining karts by Model
    public KartEntity getKartByModel(String model) {
        return kartRepository.findByModel(model);
    }

    //Service for Create a new kart
    public KartEntity updateKart(KartEntity kart) {
        return kartRepository.save(kart);
    }

    //Service for deleting a kart
    public void deleteKart(@NotNull Long id) {
        kartRepository.deleteById(id);
    }

    //Service for updating a kart by unavailability
    public KartEntity updateAvailability(@NotNull Long id, String available) {
        Optional<KartEntity> optionalKart = kartRepository.findById(id);
        if (optionalKart.isPresent()) {
            KartEntity kart = optionalKart.get();
            kart.setAvailable(available);
            return kartRepository.save(kart);
        }
        return null;
    }
}

