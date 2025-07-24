package com.example.reservation_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    private Long id;
    private int minPerson;
    private int maxPerson;
    private double percentage;

    // Getters y setters manuals
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMinPerson() {
        return minPerson;
    }
    public void setMinPerson(int minPerson) {
        this.minPerson = minPerson;
    }
    public int getMaxPerson() {
        return maxPerson;
    }
    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }
    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}