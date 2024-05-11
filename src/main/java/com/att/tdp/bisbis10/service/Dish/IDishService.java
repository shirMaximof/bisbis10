package com.att.tdp.bisbis10.service.Dish;

import com.att.tdp.bisbis10.DTO.Dish.CreateDishDTO;
import com.att.tdp.bisbis10.DTO.Dish.DishDTO;
import com.att.tdp.bisbis10.exceptions.DishNotFoundException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;

import java.util.List;

public interface IDishService {
    void addDish(Long restaurantId, CreateDishDTO createDishDTO) throws RestaurantNotFoundException;
    void updateDish(Long restaurantId, Long dishId, CreateDishDTO createDishDTO) throws DishNotFoundException, RestaurantNotFoundException;
    void deleteDish(Long restaurantId, Long dishId) throws DishNotFoundException, RestaurantNotFoundException;
    List<DishDTO> getDishesByRestaurantId(Long restaurantId) throws RestaurantNotFoundException;
}

