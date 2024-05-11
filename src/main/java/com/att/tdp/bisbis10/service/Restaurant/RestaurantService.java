package com.att.tdp.bisbis10.service.Restaurant;

import com.att.tdp.bisbis10.DTO.Dish.DishDTO;
import com.att.tdp.bisbis10.DTO.Restaurant.CreateRestaurantDTO;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWihoutDishes;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWithDishes;
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

    public List<RestaurantDTOWihoutDishes> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map((res) -> new RestaurantDTOWihoutDishes(res.getId(), res.getName(), res.getAverageRating(), res.isKosher(), res.getCuisines()))
                .collect(Collectors.toList());
    }

    public List<RestaurantDTOWihoutDishes> getRestaurantsByCuisine(String cuisine) {
        List<Restaurant> restaurants = restaurantRepository.findByCuisinesContaining(cuisine);
        return restaurants.stream()
                .map((res) -> new RestaurantDTOWihoutDishes(res.getId(), res.getName(), res.getAverageRating(), res.isKosher(), res.getCuisines()))
                .collect(Collectors.toList());
    }

    public RestaurantDTOWithDishes getRestaurantById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        Restaurant restaurant = optionalRestaurant.orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        List<DishDTO> dishes = restaurant.getDishes().stream()
                .map(dish -> new DishDTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getPrice()))
                .collect(Collectors.toList());

        return new RestaurantDTOWithDishes(restaurant.getId(), restaurant.getName(), restaurant.getAverageRating(), restaurant.isKosher(), restaurant.getCuisines(), dishes);
    }

    public void addRestaurant(CreateRestaurantDTO createRestaurantDTO) {
        Restaurant restaurant = new Restaurant(createRestaurantDTO);
        restaurantRepository.save(restaurant);
    }

    public void updateRestaurant(Long id, CreateRestaurantDTO createRestaurantDTO) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        restaurant.setName(createRestaurantDTO.name());
        restaurant.setCuisines(createRestaurantDTO.cuisines());
        restaurant.setKosher(createRestaurantDTO.isKosher());

        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) throws RestaurantNotFoundException {
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}
