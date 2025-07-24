package com.example.discount_visit_service.service;

import com.example.discount_visit_service.entity.DiscountVisitEntity;
import com.example.discount_visit_service.repository.DiscountVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountVisitService {

    @Autowired
    private DiscountVisitRepository discountRepository;

    // Assign a discount visit automatically (service method)
    public DiscountVisitEntity assignDiscount(Long clientId, int monthlyVisits) {
        double discount;
        if (monthlyVisits >= 7) {
            discount = 30.0;  //30%
        } else if (monthlyVisits >= 5) {
            discount = 20.0;  //20%
        } else if (monthlyVisits >= 2) {
            discount = 10.0;  //10%
        } else {
            discount = 0.0;   //0% for default
        }

        DiscountVisitEntity discountEntity = new DiscountVisitEntity();
        discountEntity.setClientId(clientId);
        discountEntity.setPercentage(discount);
        return discountRepository.save(discountEntity);
    }

    // Create a new discount visit (service method)
    public DiscountVisitEntity createDiscount(DiscountVisitEntity discountEntity) {
        return discountRepository.save(discountEntity);
    }

    // Update a discount visit (service method)
    public Optional<DiscountVisitEntity> updateDiscount(Long id, DiscountVisitEntity updatedData) {
        return discountRepository.findById(id).map(existing -> {
            existing.setClientId(updatedData.getClientId());
            existing.setPercentage(updatedData.getPercentage());
            return discountRepository.save(existing);
        });
    }

    // Delete a discount visit (service method)
    public boolean deleteDiscount(Long id) {
        if (discountRepository.existsById(id)) {
            discountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get discount visit by id (service method)
    public Optional<DiscountVisitEntity> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }

    // Get all discount visit (service method)
    public List<DiscountVisitEntity> getAllDiscounts() {
        return discountRepository.findAll();
    }
}
