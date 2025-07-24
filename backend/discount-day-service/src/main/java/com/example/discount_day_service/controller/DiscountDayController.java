package com.example.discount_day_service.controller;

import com.example.discount_day_service.entity.DiscountDayEntity;
import com.example.discount_day_service.service.DiscountDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts_day")
public class DiscountDayController {

    @Autowired
    private DiscountDayService discountDayService;

    // Check discount controller
    @PostMapping("/check")
    public double checkBirthdayDiscount(@RequestBody DiscountDayEntity discountRequest) {
        return discountDayService.checkAndAssignBirthdayDiscount(
                discountRequest.getClientId(),
                discountRequest.getReservationDate()
        );
    }

    // Get all discount day controller
    @GetMapping("/")
    public List<DiscountDayEntity> getAllDiscounts() {
        return discountDayService.getAllDiscounts();
    }

    // Get discount day by id controller
    @GetMapping("/{id}")
    public ResponseEntity<DiscountDayEntity> getDiscountById(@PathVariable Long id) {
        return discountDayService.getDiscountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new discount controller
    @PostMapping("/register")
    public DiscountDayEntity createDiscount(@RequestBody DiscountDayEntity discount) {
        return discountDayService.createDiscount(discount);
    }

    // Update a discount day controller
    @PutMapping("/{id}")
    public ResponseEntity<DiscountDayEntity> updateDiscount(@PathVariable Long id, @RequestBody DiscountDayEntity updated) {
        DiscountDayEntity result = discountDayService.updateDiscount(id, updated);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    // Delete a discount controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        return discountDayService.deleteDiscount(id) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Recalculate method controller
    @PutMapping("/recalculate")
    public double recalculateBirthdayDiscount(@RequestParam Long clientId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationDate) {
        return discountDayService.recalculateBirthdayDiscount(clientId, reservationDate);
    }
}