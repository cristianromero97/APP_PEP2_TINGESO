package com.example.payment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "amountPeople", nullable = false)
    private int amountPeople;

    @ElementCollection
    private List<Long> kartIds;

    @ElementCollection
    private List<Long> guestIds;

    private Long clientId;
    private Long rateId;

    @Transient
    private double discount;

    // Getters & Setters manuals
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getAmountPeople() {
        return amountPeople;
    }
    public void setAmountPeople(int amountPeople) {
        this.amountPeople = amountPeople;
    }
    public List<Long> getKartIds() {
        return kartIds;
    }
    public void setKartIds(List<Long> kartIds) {
        this.kartIds = kartIds;
    }
    public List<Long> getGuestIds() {
        return guestIds;
    }
    public void setGuestIds(List<Long> guestIds) {
        this.guestIds = guestIds;
    }
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public Long getRateId() {
        return rateId;
    }
    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
