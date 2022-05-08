package com.example.freezer.service;

import com.example.freezer.model.Food;
import com.example.freezer.model.FoodDetail;
import com.example.freezer.model.FoodSearchingCriteria;

import java.util.List;

/**
 * FreezerService is an interface that allows adding, displaying,
 * updating and searching for items inside freezer
 *
 * */
interface FreezerService {
    /**
     * If food already exists in the freezer then its quantity will increase
     * by given quantity parameter, otherwise new food will be added to the database
     * with given quantity. The food element ({@link FoodDetail}) and its
     * components cannot be null or empty. The quantity element cannot be negative.
     * Invalid parameters will throw {@link IllegalArgumentException}
     * <p>
     * @param food describes information about food
     * @return food id
     * @throws IllegalArgumentException if food or its details are null/empty or quantity is negative
     * */
    Long addFoodToFreezer(FoodDetail food) throws IllegalArgumentException;

    /**
     * Displays information about food. The id cannot be
     * null or negative. If there is no food with given
     * id then an empty {@link FoodDetail} object will be returned
     * <p>
     * @param id food id
     * @return information about the food
     * @throws IllegalArgumentException if food quantity is negative or null
     * */
    FoodDetail getFoodDetails(Long id) throws IllegalArgumentException;

    /**
     * Add/subtract food quantity. If food with given id exists
     * then update its quantity and return "Food with given id does not exist".
     * If there is no food with given id then return "No food with given id".
     * If new quantity is negative and lower then existing quantity then
     * remove food and return remainder e.g. existing = 5, new = -7 ->
     * "Food has been removed, keep remaining items: " 2. Id and quantity
     * cannot be null and id cannot be negative.
     * <p>
     * @param id food id
     * @param quantity food quantity (can be negative)
     * @return updated food quantity
     * @throws IllegalArgumentException if quantity or id is null or id is negative
     * */
    String updateFood(Long id, Long quantity) throws IllegalArgumentException;

    /**
     * Return list of foods ({@link Food}) based on given criteria ({@link FoodSearchingCriteria}).
     * If any component is null or empty then it will be ignored during filtering process,
     * e.g. if name = null then bring me all names.
     * <p>
     * @param criteria represents searching criteria for food
     * @return list of foods that meet given criteria
     * */
    List<Food> searchFood(FoodSearchingCriteria criteria);
}

