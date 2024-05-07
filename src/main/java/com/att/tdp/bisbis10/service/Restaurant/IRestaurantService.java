package com.att.tdp.bisbis10.service.Restaurant;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.model.Restaurant;

import java.util.List;

public interface IRestaurantService {
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByCuisine(String cuisine);
    Restaurant getRestaurantById(Long id);
    Restaurant addRestaurant(RestaurantDTO restaurantDTO);
    Restaurant updateRestaurant(Long id, RestaurantDTO restaurantDTO);
    void deleteRestaurant(Long id);
}
