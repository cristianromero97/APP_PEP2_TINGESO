package com.example.discount_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "discount")
@NoArgsConstructor
@AllArgsConstructor
public class DiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_person", nullable = false)
    private int minPerson;

    @Column(name = "max_person", nullable = false)
    private int maxPerson;

    @Column(name = "percentage", nullable = false)
    private double percentage;

    // Getters & Setters manuals
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMinPerson() {
        return minPerson;
    }
    public int getMaxPerson() {
        return maxPerson;
    }
    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    public void setMinPerson(int minPerson) {
        this.minPerson = minPerson;
    }
    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }
}
