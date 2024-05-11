package com.att.tdp.bisbis10.service.Restaurant;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.DTO.RestaurantDTOWithDishes;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;

import java.util.List;

public interface IRestaurantService {
    List<RestaurantDTO> getAllRestaurants();
    List<RestaurantDTO> getRestaurantsByCuisine(String cuisine);
    RestaurantDTOWithDishes getRestaurantById(Long id) throws RestaurantNotFoundException;
    void addRestaurant(RestaurantDTO restaurantDTO);
    void updateRestaurant(Long id, RestaurantDTO restaurantDTO) throws RestaurantNotFoundException;
    void deleteRestaurant(Long id) throws RestaurantNotFoundException;
}
