package com.example.kart_service.controller;

import com.example.kart_service.entity.KartEntity;
import com.example.kart_service.service.KartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/karts")
public class KartController {

    @Autowired
    private KartService kartService;

    //Controller to get all karts
    @GetMapping("/")
    public ResponseEntity<List<KartEntity>> getAllKarts() {
        return ResponseEntity.ok(kartService.getKarts());
    }

    //Controller to get a kart by ID
    @GetMapping("/{id}")
    public ResponseEntity<KartEntity> getKartById(@PathVariable Long id) {
        Optional<KartEntity> kart = kartService.getKartById(id);
        return kart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Controller to get a kart by Available
    @GetMapping("/available")
    public ResponseEntity<List<KartEntity>> getAvailableKarts() {
        return ResponseEntity.ok(kartService.getAvailableKarts());
    }

    //Controller to get a kart by Unavailable
    @GetMapping("/unavailable")
    public ResponseEntity<List<KartEntity>> getUnavailableKarts() {
        return ResponseEntity.ok(kartService.getUnavailableKarts());
    }

    //Controller to get a kart by Model
    @GetMapping("/model/{model}")
    public ResponseEntity<KartEntity> getKartByModel(@PathVariable String model) {
        KartEntity kart = kartService.getKartByModel(model);
        return kart != null ? ResponseEntity.ok(kart) : ResponseEntity.notFound().build();
    }

    //Controller to create a new kart
    @PostMapping("/register")
    public ResponseEntity<KartEntity> createKart(@RequestBody KartEntity kart) {
        return ResponseEntity.ok(kartService.updateKart(kart));
    }

    //Controller to update a kart by ID
    @PutMapping("/{id}")
    public ResponseEntity<KartEntity> updateKart(@PathVariable Long id, @RequestBody KartEntity kart) {
        kart.setId(id);
        return ResponseEntity.ok(kartService.updateKart(kart));
    }

    //Controller to update a kart by ID and new Availability
    @PutMapping("/{id}/availability/{available}")
    public ResponseEntity<KartEntity> updateKartAvailability(@PathVariable Long id, @PathVariable String available) {
        KartEntity updatedKart = kartService.updateAvailability(id, available);
        return updatedKart != null ? ResponseEntity.ok(updatedKart) : ResponseEntity.notFound().build();
    }

    //Controller to delete a kart by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKart(@PathVariable Long id) {
        kartService.deleteKart(id);
        return ResponseEntity.noContent().build();
    }
}
