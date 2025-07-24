package com.example.discount_visit_service.controller;

import com.example.discount_visit_service.entity.DiscountVisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.discount_visit_service.service.DiscountVisitService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/discounts_visit")
public class DiscountVisitController {

    @Autowired
    private DiscountVisitService discountVisitService;

    // Assing discount visit controller
    @PostMapping("/assign")
    public ResponseEntity<DiscountVisitEntity> assignDiscount(
            @RequestParam Long clientId,
            @RequestParam int monthlyVisits
    ) {
        DiscountVisitEntity discount = discountVisitService.assignDiscount(clientId, monthlyVisits);
        return ResponseEntity.ok(discount);
    }

    // Create a new discount visit controller
    @PostMapping("/register")
    public ResponseEntity<DiscountVisitEntity> createDiscount(@RequestBody DiscountVisitEntity discountEntity) {
        DiscountVisitEntity saved = discountVisitService.createDiscount(discountEntity);
        return ResponseEntity.ok(saved);
    }

    // Update a discount visit controller
    @PutMapping("/{id}")
    public ResponseEntity<DiscountVisitEntity> updateDiscount(
            @PathVariable Long id,
            @RequestBody DiscountVisitEntity updatedDiscount
    ) {
        Optional<DiscountVisitEntity> updated = discountVisitService.updateDiscount(id, updatedDiscount);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a discount visit controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        boolean deleted = discountVisitService.deleteDiscount(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Get discount visit by id controller
    @GetMapping("/{id}")
    public ResponseEntity<DiscountVisitEntity> getDiscountById(@PathVariable Long id) {
        return discountVisitService.getDiscountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all discount controller
    @GetMapping("/")
    public ResponseEntity<List<DiscountVisitEntity>> getAllDiscounts() {
        List<DiscountVisitEntity> discounts = discountVisitService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }
}
