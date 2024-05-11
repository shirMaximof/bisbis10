package com.att.tdp.bisbis10.service.Restaurant;

import com.att.tdp.bisbis10.DTO.Restaurant.CreateRestaurantDTO;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWihoutDishes;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWithDishes;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;

import java.util.List;

public interface IRestaurantService {
    List<RestaurantDTOWihoutDishes> getAllRestaurants();
    List<RestaurantDTOWihoutDishes> getRestaurantsByCuisine(String cuisine);
    RestaurantDTOWithDishes getRestaurantById(Long id) throws RestaurantNotFoundException;
    void addRestaurant(CreateRestaurantDTO createRestaurantDTO);
    void updateRestaurant(Long id, CreateRestaurantDTO createRestaurantDTO) throws RestaurantNotFoundException;
    void deleteRestaurant(Long id) throws RestaurantNotFoundException;
}
