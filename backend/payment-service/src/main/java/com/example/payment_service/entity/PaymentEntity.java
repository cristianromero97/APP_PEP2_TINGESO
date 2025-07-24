package com.example.payment_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private double discountApplied;

    @Column(nullable = false)
    private double iva;

    @Column(nullable = false)
    private double finalAmount;

    @Column(nullable = false)
    private String state;

    private Long discountId;
    private Long reservationId;
    private Long rateId;

    // Getters & Setters (manuals)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public double getDiscountApplied() {
        return discountApplied;
    }
    public void setDiscountApplied(double discountApplied) {
        this.discountApplied = discountApplied;
    }
    public double getIva() {
        return iva;
    }
    public void setIva(double iva) {
        this.iva = iva;
    }
    public double getFinalAmount() {
        return finalAmount;
    }
    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Long getDiscountId() {
        return discountId;
    }
    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }
    public Long getReservationId() {
        return reservationId;
    }
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
    public Long getRateId() {
        return rateId;
    }
    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }
}
