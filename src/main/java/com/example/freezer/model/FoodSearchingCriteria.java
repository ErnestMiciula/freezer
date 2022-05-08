package com.example.freezer.model;

import com.example.freezer.model.util.FoodType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

/* This object is used to filter foods */
@Getter
@Builder
public class FoodSearchingCriteria {
    /* Set of food names e.g. [Peas, Broccoli, Pork chop] can be empty or null */
    private Set<String> name;
    /* Set of food types e.g. [VEGETABLE, MEAT] can be empty or null */
    private Set<FoodType> type;
    /* Represents date on which food has been added to the freezer e.g. 2022-05-05 */
    private LocalDate date;
}
