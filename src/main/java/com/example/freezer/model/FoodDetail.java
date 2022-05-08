package com.example.freezer.model;

import com.example.freezer.model.util.FoodType;
import lombok.*;

/* Fields below will be used to describe in more details information about food */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDetail {
    /* Food name e.g. Peas, cannot be empty or null */
    private String name;
    /* Food type e.g. VEGETABLE, cannot be empty or null */
    private FoodType type;
    /* Food quantity e.g. 15, cannot be null or negative */
    private Long quantity;
}
