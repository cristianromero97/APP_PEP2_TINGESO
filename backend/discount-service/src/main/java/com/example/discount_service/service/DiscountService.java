package com.example.discount_service.service;

import com.example.discount_service.entity.DiscountEntity;
import com.example.discount_service.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    //Discount Obtaining Service
    public List<DiscountEntity> getAllDiscounts() {
        return discountRepository.findAll();
    }

    //Getting a Discount by ID
    public Optional<DiscountEntity> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }

    //Creating a Discount Service
    public DiscountEntity saveDiscount(DiscountEntity discount) {
        return discountRepository.save(discount);
    }

    //Deleting a Discount Service
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }

    //Updating a Discount Service
    public DiscountEntity updateDiscount(Long id, DiscountEntity updatedDiscount) {
        return discountRepository.findById(id)
                .map(discount -> {
                    discount.setMinPerson(updatedDiscount.getMinPerson());
                    discount.setMaxPerson(updatedDiscount.getMaxPerson());
                    discount.setPercentage(updatedDiscount.getPercentage());
                    return discountRepository.save(discount);
                })
                .orElse(null); // Return null if discount not present
    }

    //Service for Obtaining a Discount by Minimum Quantity
    public List<DiscountEntity> getDiscountsByMinPerson(int minPerson) {
        return discountRepository.findByMinPerson(minPerson);
    }

    //Service for Obtaining a Discount by Maximum Quantity
    public List<DiscountEntity> getDiscountsByMaxPerson(int maxPerson) {
        return discountRepository.findByMaxPerson(maxPerson);
    }

    //Service for Obtaining a Discount by percentage
    public List<DiscountEntity> getDiscountsByPercentage(double percentage) {
        return discountRepository.findByPercentage(percentage);
    }

    //Obtaining Discounts with a Percentage Greater than a Given Value
    public List<DiscountEntity> getDiscountsByPercentageGreaterThan(double percentage) {
        return discountRepository.findByPercentageGreaterThan(percentage);
    }

    //Obtain Discounts with a Percentage Less than a Given Value
    public List<DiscountEntity> getDiscountsByPercentageLessThan(double percentage) {
        return discountRepository.findByPercentageLessThan(percentage);
    }

    //Obtaining Discounts with a Percentage in a Range
    public List<DiscountEntity> getDiscountsByPercentageBetween(double minPercentage, double maxPercentage) {
        return discountRepository.findByPercentageBetween(minPercentage, maxPercentage);
    }

    //Obtain Discounts Within a Range of People
    public List<DiscountEntity> getDiscountsByPersonRange(int min, int max) {
        return discountRepository.findDiscountsByPersonRange(min, max);
    }

    //Method that Assigns the Discount Based on the Range of People with min and max person attribute
    public DiscountEntity getDiscountByPersonRange(int minPerson, int maxPerson) {
        double discount = 0.0; // Value (0%)

        //Check the range and assign the corresponding discount
        if (minPerson >= 1 && maxPerson <= 2) {
            discount = 0.0; // 0% for range 1 a 2
        } else if (minPerson >= 3 && maxPerson <= 5) {
            discount = 10.0; // 10% for range 3 a 5
        } else if (minPerson >= 6 && maxPerson <= 10) {
            discount = 20.0; // 20% for range 6 a 10
        } else if (minPerson >= 11 && maxPerson <= 15) {
            discount = 30.0; // 30% for range 11 a 15
        } else {
            throw new IllegalArgumentException("El rango de personas no es válido.");
        }
        //Create an objet DiscountEntity with discount
        DiscountEntity discountEntity = new DiscountEntity();
        discountEntity.setMinPerson(minPerson);
        discountEntity.setMaxPerson(maxPerson);
        discountEntity.setPercentage(discount);
        return discountEntity;
    }

    //Method that Assigns the Discount Based on the Range of People with amount people attribute
    public DiscountEntity getDiscountByPersonRange2(int amountPeople) {
        double discount = 0.0; // Value for defect (0%)

        //Check the range and assign the corresponding discount
        if (amountPeople >= 1 && amountPeople <= 2) {
            discount = 0.0; // 0% for range 1 a 2
        } else if (amountPeople >= 3 && amountPeople <= 5) {
            discount = 10.0; // 10% for range 3 a 5
        } else if (amountPeople >= 6 && amountPeople <= 10) {
            discount = 20.0; // 20% for range 6 a 10
        } else if (amountPeople >= 11 && amountPeople <= 15) {
            discount = 30.0; // 30% for range 11 a 15
        } else {
            discount = 5.0; // O un value for defect
        }
        //Create an objet DiscountEntity with discount
        DiscountEntity discountEntity = new DiscountEntity();
        discountEntity.setMinPerson(amountPeople);
        discountEntity.setMaxPerson(amountPeople);
        discountEntity.setPercentage(discount);
        return discountRepository.save(discountEntity);
    }

    //New method to apply a discount from reservation microservice
    public DiscountEntity createDiscount(DiscountEntity discount) {
        return discountRepository.save(discount);
    }
}
