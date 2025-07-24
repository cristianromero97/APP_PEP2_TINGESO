package com.example.discount_service.controller;

import com.example.discount_service.entity.DiscountEntity;
import com.example.discount_service.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    //Controller for getting all discounts
    @GetMapping("/")
    public List<DiscountEntity> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    //Controller for getting discounts by ID
    @GetMapping("/{id}")
    public ResponseEntity<DiscountEntity> getDiscountById(@PathVariable Long id) {
        Optional<DiscountEntity> discount = discountService.getDiscountById(id);
        return discount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Controller for creating a discount
    @PostMapping("/register")
    public DiscountEntity createDiscount2(@RequestBody DiscountEntity discount) {
        return discountService.saveDiscount(discount);
    }

    //Controller for updating a discount
    @PutMapping("/{id}")
    public ResponseEntity<DiscountEntity> updateDiscount(@PathVariable Long id, @RequestBody DiscountEntity updatedDiscount) {
        DiscountEntity updated = discountService.updateDiscount(id, updatedDiscount);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    //Controller for deleting a discount
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }

    //Controller to get discounts for a range of people
    @GetMapping("/range/{min}/{max}")
    public List<DiscountEntity> getDiscountsByPersonRange(@PathVariable int min, @PathVariable int max) {
        return discountService.getDiscountsByPersonRange(min, max);
    }

    //Controller to get discounts based on a range of people
    @GetMapping("/calculate/{minPerson}/{maxPerson}")
    public ResponseEntity<Map<String, Double>> getDiscountByPersonRange(@PathVariable int minPerson, @PathVariable int maxPerson) {
        try {
            //return ResponseEntity.ok(discountService.getDiscountByPersonRange(minPerson, maxPerson));
            double percentage = discountService.getDiscountByPersonRange(minPerson, maxPerson).getPercentage();
            return ResponseEntity.ok(Map.of("percentage", percentage));
        } catch (IllegalArgumentException e) {
            //return ResponseEntity.badRequest().body(null);
            return ResponseEntity.badRequest().body(Map.of("error", 0.0));
        }
    }

    @GetMapping("/byAmountPeople/{amountPeople}")
    public DiscountEntity getDiscountByAmountPeople(@PathVariable int amountPeople) {
        return discountService.getDiscountByPersonRange2(amountPeople);
    }

    // New method to apply a discount from reservation microservice
    @PostMapping
    public ResponseEntity<DiscountEntity> createDiscount(@RequestBody DiscountEntity discount) {
        DiscountEntity createdDiscount = discountService.createDiscount(discount);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
    }
}