package com.att.tdp.bisbis10.service.Restaurant;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Restaurant;

import java.util.List;

public interface IRestaurantService {
    List<RestaurantDTO> getAllRestaurants();
    List<RestaurantDTO> getRestaurantsByCuisine(String cuisine);
    RestaurantDTO getRestaurantById(Long id) throws RestaurantNotFoundException;
    RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);
    RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) throws RestaurantNotFoundException;
    void deleteRestaurant(Long id) throws RestaurantNotFoundException;
}
