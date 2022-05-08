package com.example.freezer.controller;

import com.example.freezer.model.Food;
import com.example.freezer.model.FoodDetail;
import com.example.freezer.model.FoodSearchingCriteria;
import com.example.freezer.service.FreezerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/food")
public class FreezerController {

    @Autowired
    FreezerServiceImpl freezerService;

    @PostMapping(value="/add")
    public ResponseEntity<?> addFood(@RequestBody FoodDetail detail) {
        Long result;
        try {
            result = freezerService.addFoodToFreezer(detail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value="/display/{id}")
    public ResponseEntity<?> getFood(@PathVariable Long id) {
        FoodDetail result;
        try {
            result = freezerService.getFoodDetails(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value="/update")
    public ResponseEntity<?> updateFood(@RequestBody Map<String, Long> request) {
        String result;
        try {
            result = freezerService.updateFood(request.get("id"), request.get("quantity"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value="/search")
    public ResponseEntity<?> searchFood(@RequestBody FoodSearchingCriteria criteria) {
        List<Food> result;
        try {
            result = freezerService.searchFood(criteria);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
