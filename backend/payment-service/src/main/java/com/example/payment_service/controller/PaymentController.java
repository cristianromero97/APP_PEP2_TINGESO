package com.example.payment_service.controller;

import com.example.payment_service.entity.PaymentEntity;
import com.example.payment_service.model.Client;
import com.example.payment_service.model.Reservation;
import com.example.payment_service.service.PaymentService;
import com.example.payment_service.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RestTemplate restTemplate;

    // Get all payments controller
    @GetMapping("/")
    public List<PaymentEntity> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // Get payments by id controller
    @GetMapping("/{id}")
    public ResponseEntity<PaymentEntity> getPaymentById(@PathVariable Long id) {
        Optional<PaymentEntity> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new payment controller
    @PostMapping("/register")
    public ResponseEntity<PaymentEntity> createPayment(@RequestBody PaymentEntity payment) {
        return ResponseEntity.ok(paymentService.savePayment(payment));
    }

    // Update state payment (PENDING - PAYED) controller
    @PutMapping("/{id}/state")
    public ResponseEntity<PaymentEntity> updatePaymentState(@PathVariable Long id, @RequestBody String newState) {
        return ResponseEntity.ok(paymentService.updatePaymentState(id, newState));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentEntity> updatePayment(@PathVariable Long id, @RequestBody PaymentEntity payment) {
        return ResponseEntity.ok(paymentService.updatePayment(id, payment));
    }

    // Delete payment controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    // Get payment with state and state controller
    @GetMapping("/state/{state}")
    public List<PaymentEntity> getPaymentsByState(@PathVariable String state) {
        return paymentService.getPaymentsByState(state);
    }

    // Send PDF controller
    @PostMapping("/{id}/send-pdf")
    public ResponseEntity<String> sendPaymentPdf(@PathVariable Long id) throws MessagingException, IOException {
        Optional<PaymentEntity> payment = paymentService.getPaymentById(id);
        if (payment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado.");
        }
        PaymentEntity paymentEntity = payment.get();
        String reservationUrl = "http://reservation-service/api/v1/reservations/" + paymentEntity.getReservationId();
        Reservation reservation;
        try {
            reservation = restTemplate.getForObject(reservationUrl, Reservation.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la reserva.");
        }
        String clientUrl = "http://client-service/api/v1/clients/" + reservation.getClientId();
        Client client;
        try {
            client = restTemplate.getForObject(clientUrl, Client.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el cliente.");
        }
        String clientEmail = client.getEmail();
        emailService.sendPaymentPdf(clientEmail, paymentEntity, "Comprobante de Pago");
        return ResponseEntity.ok("Comprobante de pago enviado correctamente.");
    }
}