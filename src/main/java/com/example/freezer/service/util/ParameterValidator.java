package com.example.freezer.service.util;

import com.example.freezer.model.Food;
import com.example.freezer.model.FoodDetail;
import com.example.freezer.model.FoodSearchingCriteria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParameterValidator {
    protected void validateParameters(FoodDetail food)  throws IllegalArgumentException{
        if (food == null) {
            log.warn("FoodDetail cannot be null");
            throw new IllegalArgumentException("FoodDetail cannot be null");
        } else if (food.getQuantity() == null || food.getType() == null || food.getName() == null || food.getName().isEmpty()) {
            log.warn("Sub parameters cannot be null or empty");
            throw new IllegalArgumentException("Sub parameters cannot be null or empty, quantity: " +
                    food.getQuantity() + " , type: " + food.getType() + ", name: " + food.getName());
        } else if (food.getQuantity() < 1) {
            log.warn("Please add at least 1 " + food.getName());
            throw new IllegalArgumentException("Please add at least 1 " + food.getName() + " , given  quantity: " + food.getQuantity());
        }
    }

    protected void validateParameters(Long id) throws  IllegalArgumentException {
        if (id == null || id < 0 ){
            log.warn("Id cannot be null or negative");
            throw new IllegalArgumentException("Id cannot be null or negative, id: " + id);
        }
    }

    protected void validateParameters(Long id, Long quantity) throws IllegalArgumentException {
        if (id == null || quantity == null || id < 0) {
            log.warn("Id and quantity cannot be null, id cannot be negative");
            throw new IllegalArgumentException("Id and quantity cannot be null, id cannot be negative. Id: " + id + " quantity: " + quantity);
        }
    }

    protected boolean validateParameters(FoodSearchingCriteria criteria, Food food) {
        return (criteria.getName() == null || criteria.getName().isEmpty() ||
                criteria.getName().contains(food.getName()) &&
                (criteria.getType() == null || criteria.getType().isEmpty() ||
                        criteria.getType().contains(food.getType())) &&
                (criteria.getDate() == null || criteria.getDate().compareTo(food.getDate()) == 0));
    }
}
