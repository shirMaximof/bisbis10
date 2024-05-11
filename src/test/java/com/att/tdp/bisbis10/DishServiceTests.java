package com.att.tdp.bisbis10;

import com.att.tdp.bisbis10.DTO.Dish.CreateDishDTO;
import com.att.tdp.bisbis10.DTO.Dish.DishDTO;
import com.att.tdp.bisbis10.DTO.Order.CreateOrderDTO;
import com.att.tdp.bisbis10.model.Dish;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.att.tdp.bisbis10.service.Dish.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;
    private CreateDishDTO dishDTO1;
    private CreateDishDTO dishDTO2;
    private CreateDishDTO updatedDish;
    @BeforeEach
    void setup()
    {
        MockitoAnnotations.openMocks(this);
        dishDTO1 = new CreateDishDTO("Pizza", "Mushrooms pizza", 12.99);
        dishDTO2 = new CreateDishDTO("Pizza", "Onions pizza", 10.99);
        updatedDish = new CreateDishDTO("Update Pizza", "UpdatePizza", 13.0);
    }
    @Test
    void testAddDish(){
        // Arrange
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act
        try
        {
            dishService.addDish(restaurantId, dishDTO1);
        }
        catch (Exception e)
        {
            fail(e);
        }

        // Assert
        verify(dishRepository, times(1)).save(any(Dish.class));
    }

    @Test
    void testUpdateDish() {
        // Arrange
        Long restaurantId = 1L;
        Long dishId = 2L;
        Dish dish = new Dish(dishDTO1);
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Act
        try {
            dishService.updateDish(restaurantId, dishId, updatedDish);
        }
        catch (Exception e) {
            fail(e);
        }
        // Assert
        assertEquals("Update Pizza", dish.getName());
        assertEquals("UpdatePizza", dish.getDescription());
        assertEquals(13.0, dish.getPrice());
    }
    @Test
    void testDeleteDish() {
        // Arrange
        Long restaurantId = 1L;
        Long dishId = 2L;
        Dish dish = new Dish();
        Restaurant restaurant = new Restaurant();
        restaurant.addDish(dish);
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Act
        try {
            dishService.deleteDish(restaurantId, dishId);
        }
        catch (Exception e)
        {
            fail(e);
        }
        // Assert
        assertFalse(restaurant.getDishes().contains(dish));
        verify(dishRepository, times(1)).delete(dish);
    }

    @Test
    void testGetDishesByRestaurantId() {
        // Arrange
        Long restaurantId = 1L;
        List<Dish> mockDishes = Arrays.asList(
                new Dish(dishDTO1),
                new Dish(dishDTO2)
        );
        when(dishRepository.findByRestaurantId(restaurantId)).thenReturn(mockDishes);

        // Act + Assert
        try
        {
            List<DishDTO> result = dishService.getDishesByRestaurantId(restaurantId);
            assertEquals(2, result.size());
            assertEquals("Pizza", result.get(0).name());
            assertEquals("Pizza", result.get(1).name());
        }
        catch (Exception e)
        {
            fail(e);
        }
    }
}
