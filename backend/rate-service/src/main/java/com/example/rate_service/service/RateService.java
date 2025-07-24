package com.example.rate_service.service;

import com.example.rate_service.entity.RateEntity;
import com.example.rate_service.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    //Service Get all rates
    public List<RateEntity> getAllRates() {
        return rateRepository.findAll();
    }

    //Service Get rate by ID
    public Optional<RateEntity> getRateById(Long id) {
        return rateRepository.findById(id);
    }

    //Service a save a new rate
    public RateEntity saveRate(RateEntity rate) {
        return rateRepository.save(rate);
    }

    //Service to delete a rate
    public void deleteRate(Long id) {
        rateRepository.deleteById(id);
    }

    //Service create a rate based on the number of rounds
    public RateEntity createRateByLaps(int laps) {
        double price;
        int maximumTimeReservation;

        switch (laps) {
            case 10:
                price = 15000;
                maximumTimeReservation = 30;
                break;
            case 15:
                price = 20000;
                maximumTimeReservation = 35;
                break;
            case 20:
                price = 25000;
                maximumTimeReservation = 40;
                break;
            default:
                throw new IllegalArgumentException("Número de vueltas no válido");
        }

        RateEntity rate = new RateEntity();
        rate.setLaps(laps);
        rate.setPrice(price);
        rate.setMaximumTimeReservation(maximumTimeReservation);

        return rateRepository.save(rate);
    }

    //Service create a rate based on the maximum time allowed
    public RateEntity createRateByMaximumTimeLaps(int maximumTimeLaps) {
        double price;
        int maximumTimeReservation;

        switch (maximumTimeLaps) {
            case 10:
                price = 15000;
                maximumTimeReservation = 30;
                break;
            case 15:
                price = 20000;
                maximumTimeReservation = 35;
                break;
            case 20:
                price = 25000;
                maximumTimeReservation = 40;
                break;
            default:
                throw new IllegalArgumentException("Tiempo máximo de vueltas no válido");
        }

        RateEntity rate = new RateEntity();
        rate.setMaximumTimeLaps(maximumTimeLaps);
        rate.setPrice(price);
        rate.setMaximumTimeReservation(maximumTimeReservation);

        return rateRepository.save(rate);
    }

    //Service to update a rate
    public RateEntity updateRate(Long id, RateEntity updatedRate) {
        return rateRepository.findById(id).map(rate -> {
            rate.setLaps(updatedRate.getLaps());
            rate.setMaximumTimeLaps(updatedRate.getMaximumTimeLaps());
            rate.setPrice(updatedRate.getPrice());
            rate.setMaximumTimeReservation(updatedRate.getMaximumTimeReservation());
            return rateRepository.save(rate);
        }).orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada con ID: " + id));
    }
}
