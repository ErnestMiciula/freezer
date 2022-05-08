package com.example.freezer.service;

import com.example.freezer.dao.FreezerRepo;
import com.example.freezer.model.Food;
import com.example.freezer.model.FoodDetail;
import com.example.freezer.model.FoodSearchingCriteria;
import com.example.freezer.service.util.ParameterValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FreezerServiceImpl extends ParameterValidator implements FreezerService {

    @Autowired
    FreezerRepo repo;

    /**
     * {@inheritDoc}
     * */
    @Override
    public Long addFoodToFreezer(FoodDetail food) throws IllegalArgumentException {
        log.info("Validating parameters");
        validateParameters(food);

        log.info("Retrieving foods from database");
        Food existingFood = repo.findByName(food.getName()).stream()
                .filter(dbFood -> dbFood.getName().equals(food.getName()))
                .findFirst()
                .orElse(null);

        if (existingFood != null) {
            updateFood(existingFood.getId(), food.getQuantity());
            log.info("Food " + food.getName() + "added successfully");
            return existingFood.getId();
        }

        return repo.save(Food.builder()
                .name(food.getName())
                .quantity(food.getQuantity())
                .type(food.getType())
                .date(LocalDate.now()).build()).getId();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public FoodDetail getFoodDetails(Long id) throws IllegalArgumentException {
        log.info("Validating parameters");
        validateParameters(id);

        log.info("Retrieving food with id: " + id);
        Optional<Food> optional = repo.findById(id);

        if (optional.isPresent()) {
            Food food = optional.get();
            return FoodDetail
                    .builder()
                    .name(food.getName())
                    .type(food.getType())
                    .quantity(food.getQuantity())
                    .build();
        }
        return FoodDetail.builder().build();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public String updateFood(Long id, Long quantity) throws IllegalArgumentException {
        log.info("Validating parameters");
        validateParameters(id, quantity);

        log.info("Retrieving food with id: " + id);
        Optional<Food> optional = repo.findById(id);

        String result = "Food with given id does not exist";

        if (optional.isPresent()) {
            Food food = optional.get();
            Long newQuantity = quantity +  food.getQuantity();
            String foodName = food.getName();
            if (newQuantity <= 0) {
                result = foodName + " has been removed";
                if (newQuantity < 0) {
                    result += " ,keep remaining " + foodName + " : " + newQuantity * (-1);
                }
                repo.delete(food);
                log.info("Food deleted");
            } else {
                food.setQuantity(newQuantity);
                repo.save(food);
                log.info("Food saved");
                result = "There are " + newQuantity + " " + foodName + " in the freezer";
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<Food> searchFood(FoodSearchingCriteria criteria) throws IllegalArgumentException{
        log.info("Retrieving foods from database");
        List<Food> foods = repo.findAll();

        if (criteria == null) return foods;

        return foods.stream()
                .filter(rawFood -> validateParameters(criteria, rawFood))
                .collect(Collectors.toList());
    }
}
