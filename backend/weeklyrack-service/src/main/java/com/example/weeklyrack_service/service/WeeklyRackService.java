package com.example.weeklyrack_service.service;

import com.example.weeklyrack_service.entity.WeeklyRackEntity;
import com.example.weeklyrack_service.repository.WeeklyRackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyRackService {

    @Autowired
    private WeeklyRackRepository weeklyRackRepository;

    // Get all racks (service method)
    public List<WeeklyRackEntity> getAllRacks() {
        return weeklyRackRepository.findAll();
    }

    //Get racks by day (service method)
    public List<WeeklyRackEntity> getRacksByDay(String day) {
        return weeklyRackRepository.findByDay(day);
    }

    // Get rack by time block (service method)
    public List<WeeklyRackEntity> getRacksByTimeBlock(String timeBlock) {
        return weeklyRackRepository.findByTimeBlock(timeBlock);
    }

    // Get rack with day and time block (service method)
    public List<WeeklyRackEntity> getRacksByDayAndTimeBlock(String day, String timeBlock) {
        return weeklyRackRepository.findByDayAndTimeBlock(day, timeBlock);
    }

    // Get rack with reservation id (service method)
    public List<WeeklyRackEntity> getRacksByReservationId(Long reservationId) {
        return weeklyRackRepository.findByReservationId(reservationId);
    }

    // Create a new rack (service method)
    public WeeklyRackEntity createRack(WeeklyRackEntity rackEntity) {
        if (rackEntity.getReservationId() == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula.");
        }
        return weeklyRackRepository.save(rackEntity);
    }

    // Update a rack (service method)
    public WeeklyRackEntity updateRack(Long id, WeeklyRackEntity updatedRack) {
        Optional<WeeklyRackEntity> existingRack = weeklyRackRepository.findById(id);
        if (existingRack.isPresent()) {
            WeeklyRackEntity rack = existingRack.get();
            rack.setDay(updatedRack.getDay());
            rack.setTimeBlock(updatedRack.getTimeBlock());
            rack.setReservationId(updatedRack.getReservationId());
            return weeklyRackRepository.save(rack);
        }
        throw new IllegalArgumentException("Rack no encontrado");
    }

    // Delete a rack (service method)
    public boolean deleteRack(Long id) {
        Optional<WeeklyRackEntity> existingRack = weeklyRackRepository.findById(id);
        if (existingRack.isPresent()) {
            weeklyRackRepository.delete(existingRack.get());
            return true;
        }
        return false;
    }

    // Delete rack by reservation id (not use for microservice)(service method)
    public void deleteReservation(Long reservationId) {
        List<WeeklyRackEntity> relatedRacks = weeklyRackRepository.findByReservationId(reservationId);
        for (WeeklyRackEntity rack : relatedRacks) {
            rack.setReservationId(null);
            weeklyRackRepository.save(rack);
        }
    }
}
