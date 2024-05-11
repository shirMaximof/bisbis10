package com.att.tdp.bisbis10.service.Dish;

import com.att.tdp.bisbis10.DTO.Dish.CreateDishDTO;
import com.att.tdp.bisbis10.DTO.Dish.DishDTO;
import com.att.tdp.bisbis10.exceptions.DishNotFoundException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Dish;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService implements IDishService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Override
    public void addDish(Long restaurantId, CreateDishDTO createDishDTO) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId));

        Dish dish = new Dish(createDishDTO);
        restaurant.addDish(dish);

        dishRepository.save(dish);
    }

    @Override
    public void updateDish(Long restaurantId, Long dishId, CreateDishDTO createDishDTO) throws DishNotFoundException, RestaurantNotFoundException {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not fount with id: "+restaurantId));

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + dishId));

        dish.setName(createDishDTO.name());
        dish.setDescription(createDishDTO.description());
        dish.setPrice(createDishDTO.price());

        dishRepository.save(dish);
    }

    @Override
    public void deleteDish(Long restaurantId, Long dishId) throws DishNotFoundException, RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not fount with id: "+restaurantId));

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + dishId));

        restaurant.removeDish(dish);
        dishRepository.delete(dish);
    }

    @Override
    public List<DishDTO> getDishesByRestaurantId(Long restaurantId) throws RestaurantNotFoundException {
        List<Dish> dishes = dishRepository.findByRestaurantId(restaurantId);

        if (dishes.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        return dishes.stream()
                .map(dish -> new DishDTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getPrice()))
                .collect(Collectors.toList());
    }
}

