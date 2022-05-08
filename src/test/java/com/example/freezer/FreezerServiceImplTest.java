package com.example.freezer;

import com.example.freezer.dao.FreezerRepo;
import com.example.freezer.model.Food;
import com.example.freezer.model.FoodDetail;
import com.example.freezer.model.FoodSearchingCriteria;
import com.example.freezer.model.util.FoodType;
import com.example.freezer.service.FreezerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FreezerServiceImplTest {

    private final static FoodDetail FOOD_DETAIL_1 = FoodDetail.builder().type(FoodType.MEAT).name("Pork chop").quantity(5L).build();
    private final static FoodDetail FOOD_DETAIL_2 = FoodDetail.builder().type(FoodType.VEGETABLE).name("Peas").quantity(2L).build();
    private final static FoodDetail FOOD_DETAIL_3 = FoodDetail.builder().type(FoodType.ICECREAM).name("Magnum").quantity(7L).build();
    private final static FoodDetail FOOD_DETAIL_4 = FoodDetail.builder().type(FoodType.VEGETABLE).name("Broccoli").quantity(9L).build();

    private final static Food FOOD_1 = Food.builder().id(1L).type(FoodType.MEAT).name("Pork chop").quantity(5L).date(LocalDate.now()).build();
    private final static Food FOOD_2 = Food.builder().id(2L).type(FoodType.VEGETABLE).name("Peas").quantity(2L).date(LocalDate.now()).build();
    private final static Food FOOD_3 = Food.builder().id(3L).type(FoodType.ICECREAM).name("Magnum").quantity(7L).date(LocalDate.now()).build();
    private final static Food FOOD_4 = Food.builder().id(4L).type(FoodType.VEGETABLE).name("Broccoli").quantity(9L).date(LocalDate.now()).build();

    private static List<Food> getNotFilteredFoods() {
        return new ArrayList<>(Arrays.asList(FOOD_1, FOOD_2, FOOD_3, FOOD_4));
    }

    @InjectMocks
    FreezerServiceImpl sut;
    @Mock
    FreezerRepo mockRepo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /* Test successful food addition */
    @Test
    void testAddFood() {
        when(mockRepo.save(any())).thenReturn(FOOD_1);
        Long id = sut.addFoodToFreezer(FOOD_DETAIL_1);
        assertEquals(1L, id);
    }

    /* Test successful food update via add method */
    @Test
    void testUpdatedFoodViaAdd() {
        when(mockRepo.findByName(any())).thenReturn(Collections.singletonList(FOOD_1));
        when(mockRepo.findById(1L)).thenReturn(Optional.of(FOOD_1));
        Long id = sut.addFoodToFreezer(FOOD_DETAIL_1);
        assertEquals(1L, id);
    }

    /* Test successful food retrieval */
    @Test
    void testGetFood() {
        when(mockRepo.findById(2L)).thenReturn(Optional.of(FOOD_2));
        FoodDetail detail = sut.getFoodDetails(2L);
        assertEquals(FOOD_DETAIL_2.getName(), detail.getName());
        assertEquals(FOOD_DETAIL_2.getQuantity(), detail.getQuantity());
        assertEquals(FOOD_DETAIL_2.getType(), detail.getType());
    }

    /* Test successful empty food retrieval */
    @Test
    void testGetEmptyFood() {
        when(mockRepo.findById(1L)).thenReturn(Optional.empty());
        FoodDetail detail = sut.getFoodDetails(1L);
        assertNull(detail.getName());
        assertNull(detail.getQuantity());
        assertNull(detail.getType());
    }

    /* Test successful food update by positive value */
    @Test
    void testFoodUpdate() {
        when(mockRepo.findById(1L)).thenReturn(Optional.of(FOOD_1));
        String result = sut.updateFood(1L, 3L);
        assertEquals("There are 8 Pork chop in the freezer", result);
    }

    /* Test successful food update by negative value */
    @Test
    void testSubtractFood() {
        when(mockRepo.findById(4L)).thenReturn(Optional.of(FOOD_4));
        String result = sut.updateFood(4L, -4L);
        assertEquals("There are 5 Broccoli in the freezer", result);
    }

    /* Test food delete */
    @Test
    void testDeleteFood() {
        when(mockRepo.findById(3L)).thenReturn(Optional.of(FOOD_3));
        String result = sut.updateFood(3L, -7L);
        assertEquals( "Magnum has been removed", result);
    }

    /* Test food delete, return remaining food*/
    @Test
    void testDeleteFoodWithReminder() {
        when(mockRepo.findById(1L)).thenReturn(Optional.of(FOOD_1));
        String result = sut.updateFood(1L, -8L);
        assertEquals( "Pork chop has been removed ,keep remaining Pork chop : 3", result);
    }

    /* Test search for food */
    @Test
    void testSearchFood() {
        when(mockRepo.findAll()).thenReturn(getNotFilteredFoods());
        FoodSearchingCriteria criteria = FoodSearchingCriteria.builder()
                .date(LocalDate.now())
                .name(new HashSet<>(Arrays.asList("Broccoli", "Magnum")))
                .type(new HashSet<>(Arrays.asList(FoodType.VEGETABLE, FoodType.ICECREAM)))
                .build();
        List<Food> result = sut.searchFood(criteria);

        assertEquals(2, result.size());
        assertEquals(FOOD_DETAIL_3.getName(), result.get(0).getName());
        assertEquals(FOOD_DETAIL_3.getQuantity(), result.get(0).getQuantity());
        assertEquals(FOOD_DETAIL_3.getType(), result.get(0).getType());
        assertEquals(FOOD_DETAIL_4.getName(), result.get(1).getName());
        assertEquals(FOOD_DETAIL_4.getQuantity(), result.get(1).getQuantity());
        assertEquals(FOOD_DETAIL_4.getType(), result.get(1).getType());
    }

    /* Test search for all foods */
    @Test
    void testSearchAllFood() {
        when(mockRepo.findAll()).thenReturn(getNotFilteredFoods());
        FoodSearchingCriteria criteria = FoodSearchingCriteria.builder()
                .date(LocalDate.now())
                .name(new HashSet<>())
                .type(new HashSet<>())
                .build();
        List<Food> result = sut.searchFood(criteria);

        assertEquals(4, result.size());
        assertEquals(FOOD_DETAIL_1.getName(), result.get(0).getName());
        assertEquals(FOOD_DETAIL_1.getQuantity(), result.get(0).getQuantity());
        assertEquals(FOOD_DETAIL_1.getType(), result.get(0).getType());
        assertEquals(FOOD_DETAIL_2.getName(), result.get(1).getName());
        assertEquals(FOOD_DETAIL_2.getQuantity(), result.get(1).getQuantity());
        assertEquals(FOOD_DETAIL_2.getType(), result.get(1).getType());
        assertEquals(FOOD_DETAIL_3.getName(), result.get(2).getName());
        assertEquals(FOOD_DETAIL_3.getQuantity(), result.get(2).getQuantity());
        assertEquals(FOOD_DETAIL_3.getType(), result.get(2).getType());
        assertEquals(FOOD_DETAIL_4.getName(), result.get(3).getName());
        assertEquals(FOOD_DETAIL_4.getQuantity(), result.get(3).getQuantity());
        assertEquals(FOOD_DETAIL_4.getType(), result.get(3).getType());
    }

    /* Throw an exception if food param inside addFoodToFreezer() is null */
    @Test
    void testAddNullFood() {
        assertThrows(IllegalArgumentException. class, () -> sut.addFoodToFreezer(null));
    }

    /* Throw an exception if quantity param inside addFoodToFreezer() is null */
    @Test
    void testAddNullFoodQuantity() {
        assertThrows(IllegalArgumentException. class, () ->
                sut.addFoodToFreezer(FoodDetail.builder().type(FoodType.MEAT).name("Pork chop").build()));
    }

    /* Throw an exception if type param inside addFoodToFreezer() is null */
    @Test
    void testAddNullFoodType() {
        assertThrows(IllegalArgumentException. class, () ->
                sut.addFoodToFreezer(FoodDetail.builder().quantity(3L).name("Pork chop").build()));
    }

    /* Throw an exception if name param inside addFoodToFreezer() is null */
    @Test
    void testAddNullFoodName() {
        assertThrows(IllegalArgumentException. class, () ->
                sut.addFoodToFreezer(FoodDetail.builder().quantity(3L).type(FoodType.MEAT).build()));
    }

    /* Throw an exception if name param inside addFoodToFreezer() is empty */
    @Test
    void testAddEmptyFoodName() {
        assertThrows(IllegalArgumentException. class, () ->
                sut.addFoodToFreezer(FoodDetail.builder().quantity(3L).type(FoodType.MEAT).name("").build()));
    }

    /* Throw an exception if quantity param inside addFoodToFreezer() is negative */
    @Test
    void testAddNegativeFoodQuantity() {
        assertThrows(IllegalArgumentException. class, () ->
                sut.addFoodToFreezer(FoodDetail.builder().quantity(-65L).type(FoodType.MEAT).name("Pork chop").build()));
    }

    /* Throw an exception if id param inside getFoodDetails() is null */
    @Test
    void testGetFoodWithNullId() {
        assertThrows(IllegalArgumentException. class, () -> sut.getFoodDetails(null));
    }

    /* Throw an exception if id param inside getFoodDetails() is negative */
    @Test
    void testGetFoodWithNegativeId() {
        assertThrows(IllegalArgumentException. class, () -> sut.getFoodDetails(-65L));
    }

    /* Throw an exception if id param inside updateFood() is negative */
    @Test
    void testUpdateFoodWithNegativeId() {
        assertThrows(IllegalArgumentException. class, () -> sut.updateFood(-65L, 5L));
    }

    /* Throw an exception if id param inside updateFood() is null */
    @Test
    void testUpdateFoodWithNullId() {
        assertThrows(IllegalArgumentException. class, () -> sut.updateFood(null, 67L));
    }

    /* Throw an exception if quantity param inside updateFood() is null */
    @Test
    void testUpdateFoodWithNullQuantity() {
        assertThrows(IllegalArgumentException. class, () -> sut.updateFood(-65L, null));
    }


}

