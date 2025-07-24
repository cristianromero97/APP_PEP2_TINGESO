package com.example.rate_service.controller;

import com.example.rate_service.entity.RateEntity;
import com.example.rate_service.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    //Controller to get all rates
    @GetMapping("/")
    public List<RateEntity> getAllRates() {
        return rateService.getAllRates();
    }

    //Controller to get rates by ID
    @GetMapping("/{id}")
    public ResponseEntity<RateEntity> getRateById(@PathVariable Long id) {
        Optional<RateEntity> rate = rateService.getRateById(id);
        return rate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Controller to create a rate
    @PostMapping("/register")
    public RateEntity createRate(@RequestBody RateEntity rate) {
        return rateService.saveRate(rate);
    }

    //Controller to delete a rate by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        if (rateService.getRateById(id).isPresent()) {
            rateService.deleteRate(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Controller to create a rate by Laps
    @PostMapping("/createByLaps/{laps}")
    public ResponseEntity<RateEntity> createRateByLaps(@PathVariable int laps) {
        try {
            RateEntity rate = rateService.createRateByLaps(laps);
            return ResponseEntity.ok(rate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Controller to create a rate by Time
    @PostMapping("/createByMaximumTimeLaps/{maximumTimeLaps}")
    public ResponseEntity<RateEntity> createRateByMaximumTimeLaps(@PathVariable int maximumTimeLaps) {
        try {
            RateEntity rate = rateService.createRateByMaximumTimeLaps(maximumTimeLaps);
            return ResponseEntity.ok(rate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Controller to update a rate by ID
    @PutMapping("/{id}")
    public ResponseEntity<RateEntity> updateRate(@PathVariable Long id, @RequestBody RateEntity updatedRate) {
        Optional<RateEntity> rateOptional = rateService.getRateById(id);

        if (rateOptional.isPresent()) {
            RateEntity savedRate = rateService.updateRate(id, updatedRate);
            return ResponseEntity.ok(savedRate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}