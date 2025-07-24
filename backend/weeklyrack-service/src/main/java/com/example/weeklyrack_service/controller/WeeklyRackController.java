package com.example.weeklyrack_service.controller;

import com.example.weeklyrack_service.entity.WeeklyRackEntity;
import com.example.weeklyrack_service.service.WeeklyRackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/weeklyRacks")
public class WeeklyRackController {

    @Autowired
    private WeeklyRackService weeklyRackService;

    //Controller to get all racks controller
    @GetMapping("/")
    public ResponseEntity<List<WeeklyRackEntity>> getAllRacks() {
        return ResponseEntity.ok(weeklyRackService.getAllRacks());
    }

    //Controller to get a rack by Day controller
    @GetMapping("/day/{day}")
    public ResponseEntity<List<WeeklyRackEntity>> getRacksByDay(@PathVariable String day) {
        return ResponseEntity.ok(weeklyRackService.getRacksByDay(day));
    }

    //Controller to get a rack by Time-Block controller
    @GetMapping("/timeBlock/{timeBlock}")
    public ResponseEntity<List<WeeklyRackEntity>> getRacksByTimeBlock(@PathVariable String timeBlock) {
        return ResponseEntity.ok(weeklyRackService.getRacksByTimeBlock(timeBlock));
    }

    //Controller to get a rack by Day and Time-Block controller
    @GetMapping("/day/{day}/timeBlock/{timeBlock}")
    public ResponseEntity<List<WeeklyRackEntity>> getRacksByDayAndTimeBlock(
            @PathVariable String day, @PathVariable String timeBlock) {
        return ResponseEntity.ok(weeklyRackService.getRacksByDayAndTimeBlock(day, timeBlock));
    }

    //Controller to get a rack by Reservation ID controller
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<WeeklyRackEntity>> getRacksByReservationId(@PathVariable Long reservationId) {
        return ResponseEntity.ok(weeklyRackService.getRacksByReservationId(reservationId));
    }

    //Controller to create a new rack controller
    @PostMapping("/register")
    public ResponseEntity<WeeklyRackEntity> createRack(@RequestBody WeeklyRackEntity rackEntity) {
        return ResponseEntity.ok(weeklyRackService.createRack(rackEntity));
    }

    //Controller to update a rack by ID controller
    @PutMapping("/{id}")
    public ResponseEntity<WeeklyRackEntity> updateRack(@PathVariable Long id, @RequestBody WeeklyRackEntity updatedRack) {
        return ResponseEntity.ok(weeklyRackService.updateRack(id, updatedRack));
    }

    //Controller to delete a rack by ID controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRack(@PathVariable Long id) {
        if (weeklyRackService.deleteRack(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
