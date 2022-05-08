package com.example.freezer.model;

import com.example.freezer.model.util.FoodType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/* Represents food information */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {
    /* Food id e.g. 1, cannot be negative or null */
    @Id
    @GeneratedValue
    private Long id;
    /* Food name e.g. Peas, cannot be empty or null */
    private String name;
    /* Food type e.g. VEGETABLE, cannot be empty or null */
    private FoodType type;
    /* Food quantity e.g. 15, cannot be null or negative */
    private Long quantity;
    /* Represents date on which food has been added to the freezer e.g. 2022-05-05 */
    private LocalDate date;
}
