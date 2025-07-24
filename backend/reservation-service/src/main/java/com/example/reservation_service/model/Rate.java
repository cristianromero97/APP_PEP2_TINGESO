package com.example.reservation_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private Long id;
    private int laps;
    private int maximumTimeLaps;
    private double price;
    private int maximumTimeReservation;

    // Getters y setters manuals
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLaps() {
        return laps;
    }
    public void setLaps(int laps) {
        this.laps = laps;
    }
    public int getMaximumTimeLaps() {
        return maximumTimeLaps;
    }
    public void setMaximumTimeLaps(int maximumTimeLaps) {
        this.maximumTimeLaps = maximumTimeLaps;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getMaximumTimeReservation() {
        return maximumTimeReservation;
    }
    public void setMaximumTimeReservation(int maximumTimeReservation) {
        this.maximumTimeReservation = maximumTimeReservation;
    }
}
