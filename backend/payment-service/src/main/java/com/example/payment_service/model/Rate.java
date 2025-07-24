package com.example.payment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "laps", nullable = false)
    private int laps;

    @Column(name = "maximumTimeLaps", nullable = false)
    private int maximumTimeLaps;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "maximumTimeReservation", nullable = false)
    private int maximumTimeReservation;

    // Getters & Setters manuals
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
