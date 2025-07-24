package com.example.weeklyrack_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weeklyRack")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyRackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private String timeBlock;

    @Column(name = "reservation_id")
    private Long reservationId;

    // Getters & Setters (manuals)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getTimeBlock() {
        return timeBlock;
    }
    public void setTimeBlock(String timeBlock) {
        this.timeBlock = timeBlock;
    }
    public Long getReservationId() {
        return reservationId;
    }
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}