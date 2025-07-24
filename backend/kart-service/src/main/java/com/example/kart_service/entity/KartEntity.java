package com.example.kart_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "kart")
@NoArgsConstructor
@AllArgsConstructor
public class KartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "kart_seq")
    @SequenceGenerator(name = "kart_seq", sequenceName = "kart_id_seq", allocationSize = 1)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "model", nullable = false)
    private String model; // Example: K001, K002,K003 until K015 ...

    @Column(name = "available", nullable = false)
    private String available;

    // Getters & Setters (manuals)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getAvailable() {
        return available;
    }
    public void setAvailable(String available) {
        this.available = available;
    }
}