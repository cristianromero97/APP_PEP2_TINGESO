package com.example.report_service.service;

import com.example.report_service.entity.ReportEntity;
import com.example.report_service.model.Payment;
import com.example.report_service.model.Rate;
import com.example.report_service.model.Reservation;
import com.example.report_service.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Connections with other microservice
    private final String PAYMENT_SERVICE_URL = "http://payment-service/api/v1/payments/";
    private final String RESERVATION_SERVICE_URL = "http://reservation-service/api/v1/reservations/";
    private final String RATE_SERVICE_URL = "http://rate-service/api/v1/rates/";

    // Create a new report (service method)
    public ReportEntity generateReport(ReportEntity reportEntity) {
        Payment[] payments = restTemplate.getForObject(PAYMENT_SERVICE_URL, Payment[].class);
        if (payments == null) payments = new Payment[0];

        List<Long> paymentIds = new ArrayList<>();
        double totalRevenue = 0;
        int totalPeople = 0;
        int totalLaps = 0;
        double avgPerPerson = 0;
        double avgPerLaps = 0;

        String type = reportEntity.getType();

        for (Payment payment : payments) {
            Long reservationId = payment.getReservationId();
            Long rateId = payment.getRateId();

            if (reservationId == null || rateId == null) continue;

            Reservation reservation = null;
            Rate rate = null;

            try {
                reservation = restTemplate.getForObject(RESERVATION_SERVICE_URL + reservationId, Reservation.class);
                rate = restTemplate.getForObject(RATE_SERVICE_URL + rateId, Rate.class);
            } catch (Exception e) {
                System.out.println("Error consultando reservation/rate: " + e.getMessage());
                continue;
            }

            if (reservation == null || rate == null) continue;

            paymentIds.add(payment.getId());

            switch (type.toLowerCase()) {
                case "time":
                    if (rate.getMaximumTimeLaps() > 0) {
                        totalRevenue += payment.getFinalAmount();
                        totalLaps += rate.getMaximumTimeLaps();
                    }
                    break;
                case "laps":
                    if (rate.getLaps() > 0) {
                        totalRevenue += payment.getFinalAmount();
                        totalLaps += rate.getLaps();
                    }
                    break;
                case "people":
                    totalRevenue += payment.getFinalAmount();
                    totalPeople += reservation.getAmountPeople();
                    break;
            }
        }

        if (type.equalsIgnoreCase("people")) {
            avgPerPerson = totalPeople == 0 ? 0 : totalRevenue / totalPeople;
        } else {
            avgPerLaps = totalLaps == 0 ? 0 : totalRevenue / totalLaps;
        }

        reportEntity.setTotalRevenue(totalRevenue);
        reportEntity.setTotalPeople(totalPeople);
        reportEntity.setTotalLaps(totalLaps);
        reportEntity.setAveragePerPerson(avgPerPerson);
        reportEntity.setAveragePerLaps(avgPerLaps);
        reportEntity.setPaymentIds(paymentIds);
        reportEntity.setDateGeneration(LocalDate.now());

        return reportRepository.save(reportEntity);
    }

    // Get all reports (service method)
    public List<ReportEntity> getAllReports() {
        return reportRepository.findAll();
    }

    // Get report by id (service method)
    public Optional<ReportEntity> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    // Update a report (service method)
    public ReportEntity updateReport(Long id, ReportEntity updated) {
        if (reportRepository.existsById(id)) {
            updated.setId(id);
            return reportRepository.save(updated);
        } else {
            throw new RuntimeException("Reporte no encontrado con id " + id);
        }
    }

    // Delete a report (service method)
    public void deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reporte no encontrado con id " + id);
        }
    }
}
