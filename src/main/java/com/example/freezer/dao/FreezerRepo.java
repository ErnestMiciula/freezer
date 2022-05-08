package com.example.freezer.dao;

import com.example.freezer.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreezerRepo extends JpaRepository<Food, Long> {
    List<Food> findByName(String name);
}
