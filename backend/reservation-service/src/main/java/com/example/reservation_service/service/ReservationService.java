package com.example.reservation_service.service;

import com.example.reservation_service.entity.ReservationEntity;
import com.example.reservation_service.model.*;
import com.example.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Connections with other microservice
    private final String CLIENT_SERVICE = "http://client-service/api/v1/clients/";
    private final String KART_SERVICE = "http://kart-service/api/v1/karts/";
    private final String RATE_SERVICE = "http://rate-service/api/v1/rates/";
    private final String DISCOUNT_SERVICE = "http://discount-service/api/v1/discounts/byAmountPeople/";
    private final String DISCOUNT_DAY_SERVICE = "http://discount-day-service/api/v1/discounts_day/check";
    private final String WEEKLY_RACK_SERVICE_URL = "http://weeklyrack-service/api/v1/weeklyRacks/reservation/";

    // Get all reservation (service method)
    public List<ReservationEntity> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Get reservation by id (service method)
    public Optional<ReservationEntity> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    // Create a new reservation (service method)
    public ReservationEntity createReservation(ReservationEntity reservation) {
        // Validate client
        Client client = restTemplate.getForObject(CLIENT_SERVICE + reservation.getClientId(), Client.class);
        if (client == null) throw new RuntimeException("Cliente no encontrado");

        // Validate rate
        Rate rate = restTemplate.getForObject(RATE_SERVICE + reservation.getRateId(), Rate.class);
        if (rate == null) throw new RuntimeException("Tarifa no encontrada");

        // Mark karts as unavailable
        for (Long kartId : reservation.getKartIds()) {
            Kart kart = restTemplate.getForObject(KART_SERVICE + kartId, Kart.class);
            if (kart == null) throw new RuntimeException("Kart no encontrado con ID: " + kartId);
            kart.setAvailable("No Disponible");
            restTemplate.put(KART_SERVICE + kartId + "/availability/No Disponible", kart);
        }

        // Get Discount correctly endpoint
        Discount discount = restTemplate.getForObject(
                DISCOUNT_SERVICE + reservation.getAmountPeople(), Discount.class);

        if (discount == null) {
            discount = new Discount();
            discount.setPercentage(0.0);

            // Create a new discount if not exist
            discount = restTemplate.postForObject(DISCOUNT_SERVICE, discount, Discount.class);
        }
        reservation.setDiscount(discount.getPercentage());

        // Create a new discount for birthday
        DiscountDay request = new DiscountDay();
        request.setClientId(reservation.getClientId());
        request.setReservationDate(reservation.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        restTemplate.postForObject(
                DISCOUNT_DAY_SERVICE,
                request,
                Double.class
        );

        // Save reservation
        return reservationRepository.save(reservation);
    }

    // Update a reservation (service method)
    public ReservationEntity updateReservation(Long id, ReservationEntity updatedReservation) {
        return reservationRepository.findById(id).map(reservation -> {
            // Free karts
            for (Long kartId : reservation.getKartIds()) {
                Kart kart = restTemplate.getForObject(KART_SERVICE + kartId, Kart.class);
                if (kart != null) {
                    kart.setAvailable("Disponible");
                    restTemplate.put(KART_SERVICE + kartId + "/availability/Disponible", kart);
                }
            }

            // Update info
            reservation.setDate(updatedReservation.getDate());
            reservation.setAmountPeople(updatedReservation.getAmountPeople());
            reservation.setClientId(updatedReservation.getClientId());
            reservation.setRateId(updatedReservation.getRateId());
            reservation.setKartIds(updatedReservation.getKartIds());
            reservation.setGuestIds(updatedReservation.getGuestIds());

            // Mark new karts as unavailable
            for (Long kartId : updatedReservation.getKartIds()) {
                Kart kart = restTemplate.getForObject(KART_SERVICE + kartId, Kart.class);
                if (kart != null) {
                    kart.setAvailable("No Disponible");
                    restTemplate.put(KART_SERVICE + kartId + "/availability/No Disponible", kart);
                }
            }

            // Get discount (update)
            Discount discount = restTemplate.getForObject(
                    DISCOUNT_SERVICE + updatedReservation.getAmountPeople(), Discount.class);

            if (discount == null) {
                discount = new Discount();
                discount.setPercentage(0.0);
                discount = restTemplate.postForObject(DISCOUNT_SERVICE, discount, Discount.class);
            }

            reservation.setDiscount(discount.getPercentage());
            return reservationRepository.save(reservation);
        }).orElse(null);
    }

    // Delete a reservation (service method)
    public boolean deleteReservation(Long reservationId) {
        Optional<ReservationEntity> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            ReservationEntity reservation = optionalReservation.get();

            // Free karts
            for (Long kartId : reservation.getKartIds()) {
                Kart kart = restTemplate.getForObject(KART_SERVICE + kartId, Kart.class);
                if (kart != null) {
                    kart.setAvailable("Disponible");
                    restTemplate.put(KART_SERVICE + kartId + "/availability/Disponible", kart);
                }
            }
            try {
                restTemplate.delete(WEEKLY_RACK_SERVICE_URL + reservationId);
            } catch (Exception e) {
                // Error handling, in case the rack iss down
                System.err.println("Error al notificar a weeklyrack-service: " + e.getMessage());
            }

            reservationRepository.delete(reservation);
            return true;
        }
        return false;
    }

    // Create a new reservation with group and id client that made reservation (service method)
    public ReservationEntity createGroupReservation(Long clientId, ReservationEntity reservation) {
        Client client = restTemplate.getForObject(CLIENT_SERVICE + clientId, Client.class);
        if (client == null) throw new RuntimeException("Cliente no encontrado");

        if (reservation.getAmountPeople() <= 1) {
            throw new RuntimeException("Se requiere al menos una persona adicional para una reservación grupal");
        }

        reservation.setClientId(clientId);

        List<Long> guests = new ArrayList<>(reservation.getGuestIds());

        // Add dummy guest (example)
        for (long i = 2; i <= reservation.getAmountPeople(); i++) {
            Client guest = restTemplate.getForObject(CLIENT_SERVICE + i, Client.class);
            if (guest != null) {
                guests.add(guest.getId());
            }
        }

        // Delete last guest
        if (!guests.isEmpty()) {
            guests.remove(guests.size() - 1);
        }

        reservation.setGuestIds(guests);

        // Get discount
        Discount discount = restTemplate.getForObject(
                DISCOUNT_SERVICE + reservation.getAmountPeople(), Discount.class);

        if (discount == null) {
            discount = new Discount();
            discount.setPercentage(0.0);
            discount = restTemplate.postForObject(DISCOUNT_SERVICE, discount, Discount.class);
        }

        reservation.setDiscount(discount.getPercentage());

        // Create a discount for birthday
        DiscountDay request = new DiscountDay();
        request.setClientId(reservation.getClientId());
        request.setReservationDate(reservation.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        restTemplate.postForObject(
                DISCOUNT_DAY_SERVICE,
                request,
                Double.class
        );
        return reservationRepository.save(reservation);
    }

}
