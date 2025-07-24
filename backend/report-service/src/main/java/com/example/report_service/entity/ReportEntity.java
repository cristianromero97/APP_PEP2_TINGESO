package com.example.report_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "report")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(name = "date_generation", nullable = false)
    private LocalDate dateGeneration;

    @Column(name = "total_revenue", nullable = false)
    private double totalRevenue;

    @Column(name = "total_people", nullable = false)
    private int totalPeople;

    @Column(name = "total_laps", nullable = false)
    private int totalLaps;

    @Column(name = "average_per_person", nullable = false)
    private double averagePerPerson;

    @Column(name = "average_per_laps", nullable = false)
    private double averagePerLaps;

    @ElementCollection
    private List<Long> paymentIds;

    // Getters y setters manuals
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDate getDateGeneration() { return dateGeneration; }
    public void setDateGeneration(LocalDate dateGeneration) { this.dateGeneration = dateGeneration; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    public int getTotalPeople() { return totalPeople; }
    public void setTotalPeople(int totalPeople) { this.totalPeople = totalPeople; }
    public int getTotalLaps() { return totalLaps; }
    public void setTotalLaps(int totalLaps) { this.totalLaps = totalLaps; }
    public double getAveragePerPerson() { return averagePerPerson; }
    public void setAveragePerPerson(double averagePerPerson) { this.averagePerPerson = averagePerPerson; }
    public double getAveragePerLaps() { return averagePerLaps; }
    public void setAveragePerLaps(double averagePerLaps) { this.averagePerLaps = averagePerLaps; }
    public List<Long> getPaymentIds() { return paymentIds; }
    public void setPaymentIds(List<Long> paymentIds) { this.paymentIds = paymentIds; }
}
