package com.example.payment_service.service;

import com.example.payment_service.entity.PaymentEntity;
import com.example.payment_service.model.Reservation;
import com.example.payment_service.model.Rate;
import com.example.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Connections with other microservice
    private final String RESERVATION_SERVICE = "http://reservation-service/api/v1/reservations/";
    private final String DISCOUNT_DAY_SERVICE = "http://discount-day-service/api/v1/discounts_day/check";
    private final String DISCOUNT_VISIT_SERVICE = "http://discount-visit-service/api/v1/discounts_visit/assign";
    private final String DISCOUNT_SERVICE = "http://discount-service/api/v1/discounts/byAmountPeople/";
    private final String RATE_SERVICE = "http://rate-service/api/v1/rates/";

    // Generate
    private static final Map<Long, Integer> mockMonthlyVisits = Map.of(
            9L, 8,
            2L, 5,
            5L, 3,
            6L, 0
    );

    // Get all payments (service method)
    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by id (service method)
    public Optional<PaymentEntity> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    // Create a new payment (service method)
    public PaymentEntity savePayment(PaymentEntity payment) {
        Long reservationId = payment.getReservationId();

        System.out.println("Consultando reserva: " + RESERVATION_SERVICE + reservationId);
        Reservation reservation = restTemplate.getForObject(RESERVATION_SERVICE + reservationId, Reservation.class);
        if (reservation == null) throw new RuntimeException("Reservation not found");

        int amountPeople = reservation.getAmountPeople();
        List<Long> guestIds = reservation.getGuestIds();
        Long clientId = reservation.getClientId();
        Long rateId = reservation.getRateId();
        LocalDate reservationDate = reservation.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        payment.setRateId(rateId);

        System.out.println("Consultando tarifa: " + RATE_SERVICE + rateId);
        Rate rate = restTemplate.getForObject(RATE_SERVICE + rateId, Rate.class);
        if (rate == null) throw new RuntimeException("Rate not found");
        double basePrice = rate.getPrice();

        List<Long> allClientIds = new ArrayList<>();
        allClientIds.add(clientId);
        if (guestIds != null) guestIds.forEach(allClientIds::add);

        double maxDiscount = 0.0;
        for (Long id : allClientIds) {
            Double dayDiscount = 0.0;
            Double visitDiscount = 0.0;

            try {
                Map<String, Object> body = Map.of("clientId", id, "reservationDate", reservationDate.toString());
                System.out.println("Check cumpleaños: " + DISCOUNT_DAY_SERVICE + " con body " + body);
                dayDiscount = restTemplate.postForObject(DISCOUNT_DAY_SERVICE, body, Double.class);
            } catch (RestClientException e) {
                System.out.println("Error al consultar descuento por cumpleaños para clientId " + id + ": " + e.getMessage());
            }

            try {
                int monthlyVisits = mockMonthlyVisits.getOrDefault(id, 0);
                String url = DISCOUNT_VISIT_SERVICE + "?clientId=" + id + "&monthlyVisits=" + monthlyVisits;
                System.out.println("Assign visitas: " + url);
                Map visitResponse = restTemplate.postForObject(url, null, Map.class);
                if (visitResponse != null && visitResponse.get("percentage") != null) {
                    visitDiscount = Double.parseDouble(visitResponse.get("percentage").toString());
                }
            } catch (RestClientException e) {
                System.out.println("Error al consultar descuento por visitas para clientId " + id + ": " + e.getMessage());
            }

            maxDiscount = Math.max(maxDiscount, Math.max(
                    dayDiscount != null ? dayDiscount : 0,
                    visitDiscount != null ? visitDiscount : 0
            ));
        }

        System.out.println("Consultando descuento grupal: " + DISCOUNT_SERVICE + amountPeople);
        try {
            Map discount = restTemplate.getForObject(DISCOUNT_SERVICE + amountPeople, Map.class);
            double groupDiscount = (discount != null && discount.get("percentage") != null)
                    ? Double.parseDouble(discount.get("percentage").toString()) : 0.0;
            maxDiscount = Math.max(maxDiscount, groupDiscount);
        } catch (RestClientException e) {
            System.out.println("Error al consultar descuento grupal: " + e.getMessage());
        }

        double totalAmount = basePrice * amountPeople;
        double discountAmount = totalAmount * maxDiscount / 100.0;
        double iva = 3000.0;
        double finalAmount = Math.max(0, totalAmount - discountAmount + iva);

        payment.setTotalAmount(totalAmount);
        payment.setDiscountApplied(discountAmount);
        payment.setIva(iva);
        payment.setFinalAmount(finalAmount);

        return paymentRepository.save(payment);
    }

    // Delete payment (service method)
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    // Get payment by state (service method)
    public List<PaymentEntity> getPaymentsByState(String state) {
        return paymentRepository.findByState(state);
    }

    // Update a payment state (service method)
    public PaymentEntity updatePaymentState(Long id, String newState) {
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow();
        payment.setState(newState);
        return paymentRepository.save(payment);
    }

    // Update a payment (service method)
    public PaymentEntity updatePayment(Long id, PaymentEntity updated) {
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow();
        payment.setTotalAmount(updated.getTotalAmount());
        payment.setDiscountApplied(updated.getDiscountApplied());
        payment.setIva(updated.getIva());
        payment.setFinalAmount(updated.getFinalAmount());
        payment.setState(updated.getState());
        payment.setReservationId(updated.getReservationId());
        payment.setDiscountId(updated.getDiscountId());
        payment.setRateId(updated.getRateId());
        return paymentRepository.save(payment);
    }
}
