package com.example.discount_day_service.service;

import com.example.discount_day_service.entity.DiscountDayEntity;
import com.example.discount_day_service.model.Client;
import com.example.discount_day_service.repository.DiscountDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountDayService {

    // Connection with other microservice
    private static final String CLIENT_SERVICE_URL = "http://client-service/api/v1/clients/";

    @Autowired
    private DiscountDayRepository discountDayRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Check assign birthday discount (service method)
    public double checkAndAssignBirthdayDiscount(Long clientId, LocalDate reservationDate) {
        Client client = restTemplate.getForObject(CLIENT_SERVICE_URL + clientId, Client.class);

        if (client == null || client.getBirthDate() == null) {
            System.out.println("Cliente no encontrado o sin fecha de nacimiento.");
            return 0.0;
        }

        boolean isBirthday = client.getBirthDate().getDayOfMonth() == reservationDate.getDayOfMonth()
                && client.getBirthDate().getMonth() == reservationDate.getMonth();

        double discountPercentage = isBirthday ? 50.0 : 0.0; //50%

        // Save always result
        DiscountDayEntity discount = new DiscountDayEntity();
        discount.setClientId(clientId);
        discount.setReservationDate(reservationDate);
        discount.setPercentage(discountPercentage);
        discountDayRepository.save(discount);

        if (isBirthday) {
            System.out.println("Descuento por cumpleaños aplicado y guardado.");
        } else {
            System.out.println("No aplica descuento por cumpleaños, pero se registró como 0.0.");
        }

        return discountPercentage;
    }

    // Create a new discount day (service method)
    public DiscountDayEntity createDiscount(DiscountDayEntity discount) {
        return discountDayRepository.save(discount);
    }

    // Get all discounts day (service method)
    public List<DiscountDayEntity> getAllDiscounts() {
        return discountDayRepository.findAll();
    }

    // Get discount day by id (service method)
    public Optional<DiscountDayEntity> getDiscountById(Long id) {
        return discountDayRepository.findById(id);
    }

    // Update discount day (service method)
    public DiscountDayEntity updateDiscount(Long id, DiscountDayEntity updated) {
        return discountDayRepository.findById(id).map(d -> {
            d.setClientId(updated.getClientId());
            d.setReservationDate(updated.getReservationDate());
            d.setPercentage(updated.getPercentage());
            return discountDayRepository.save(d);
        }).orElse(null);
    }

    // Delete discount day (service method)
    public boolean deleteDiscount(Long id) {
        if (discountDayRepository.existsById(id)) {
            discountDayRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Recalculate birthday discount day (service method)
    public double recalculateBirthdayDiscount(Long clientId, LocalDate reservationDate) {
        List<DiscountDayEntity> existingDiscounts =
                discountDayRepository.findByClientIdAndReservationDate(clientId, reservationDate);
        // Delete discount
        if (!existingDiscounts.isEmpty()) {
            discountDayRepository.deleteAll(existingDiscounts);
        }
        // Get clients (update)
        Client client = restTemplate.getForObject(CLIENT_SERVICE_URL + clientId, Client.class);
        if (client == null || client.getBirthDate() == null) return 0.0;
        // If not coincidence, simply not create a new discount again
        if (client.getBirthDate().getDayOfMonth() != reservationDate.getDayOfMonth() ||
                client.getBirthDate().getMonth() != reservationDate.getMonth()) {
            return 0.0;
        }
        // Coincidence -> create a new discount again
        DiscountDayEntity discount = new DiscountDayEntity();
        discount.setClientId(clientId);
        discount.setReservationDate(reservationDate);
        discount.setPercentage(0.5);
        discountDayRepository.save(discount);
        return 50.0; //50%
    }
}