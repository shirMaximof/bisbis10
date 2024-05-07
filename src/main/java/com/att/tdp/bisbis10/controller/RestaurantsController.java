package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.service.Restaurant.IRestaurantService;
import com.att.tdp.bisbis10.service.Restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantsController {
    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping(params = "cuisine")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(@RequestParam("cuisine") String cuisine) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        Restaurant createdRestaurant = restaurantService.addRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

}
