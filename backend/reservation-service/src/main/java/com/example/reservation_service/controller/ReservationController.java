package com.example.reservation_service.controller;

import com.example.reservation_service.entity.ReservationEntity;
import com.example.reservation_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Get all reservations controller
    @GetMapping("/")
    public List<ReservationEntity> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Get reservation by id controller
    @GetMapping("/{id}")
    public ResponseEntity<ReservationEntity> getReservationById(@PathVariable Long id) {
        Optional<ReservationEntity> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new reservation controller
    @PostMapping("/register")
    public ReservationEntity createReservation(@RequestBody ReservationEntity reservation) {
        return reservationService.createReservation(reservation);
    }

    // Update a reservation controller
    @PutMapping("/{id}")
    public ResponseEntity<ReservationEntity> updateReservation(@PathVariable Long id, @RequestBody ReservationEntity updatedReservation) {
        ReservationEntity reservation = reservationService.updateReservation(id, updatedReservation);
        return (reservation != null) ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }

    // Delete a reservation controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        return reservationService.deleteReservation(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Create a new reservation with group and clientID (Id that made reservation client) controller
    @PostMapping("/group/{clientId}")
    public ResponseEntity<ReservationEntity> createGroupReservation(@PathVariable Long clientId, @RequestBody ReservationEntity reservation) {
        try {
            ReservationEntity newReservation = reservationService.createGroupReservation(clientId, reservation);
            return ResponseEntity.ok(newReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}