package com.att.tdp.bisbis10.service.Restaurant;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map((res) -> new RestaurantDTO(res.getId(), res.getName(), res.isKosher(), res.getAverageRating(), res.getCuisines())) // Assuming RestaurantDTO constructor takes a Restaurant as parameter
                .collect(Collectors.toList());
    }

    public List<RestaurantDTO> getRestaurantsByCuisine(String cuisine) {
        List<Restaurant> restaurants = restaurantRepository.findByCuisinesContaining(cuisine);

        return restaurants.stream()
                .map((res) -> new RestaurantDTO(res.getId(), res.getName(), res.isKosher(), res.getAverageRating(), res.getCuisines())) // Assuming RestaurantDTO constructor takes a Restaurant as parameter
                .collect(Collectors.toList());
    }

    public RestaurantDTO getRestaurantById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        Restaurant restaurant = optionalRestaurant.orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.isKosher(), restaurant.getAverageRating(), restaurant.getCuisines());
    }

    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant(restaurantDTO);
        restaurantRepository.save(restaurant);
        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.isKosher(), restaurant.getAverageRating(), restaurant.getCuisines());
    }

    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        restaurant.setName(restaurantDTO.name());
        restaurant.setCuisines(restaurantDTO.cuisines());
        restaurant.setKosher(restaurantDTO.isKosher());

        restaurantRepository.save(restaurant);
        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.isKosher(), restaurant.getAverageRating(), restaurant.getCuisines());
    }

    public void deleteRestaurant(Long id) throws RestaurantNotFoundException {
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}
