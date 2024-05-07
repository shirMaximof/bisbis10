package com.att.tdp.bisbis10.service.Restaurant;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisinesContaining(cuisine);
    }

    public Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));
    }

    public Restaurant addRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant(restaurantDTO);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, RestaurantDTO restaurantDTO) throws RestaurantNotFoundException {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        existingRestaurant.setName(restaurantDTO.name());
        existingRestaurant.setCuisines(restaurantDTO.cuisines());
        existingRestaurant.setKosher(restaurantDTO.isKosher());

        return restaurantRepository.save(existingRestaurant);
    }

    public void deleteRestaurant(Long id) throws RestaurantNotFoundException {
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}
