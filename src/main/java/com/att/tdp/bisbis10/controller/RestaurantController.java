package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.Restaurant.CreateRestaurantDTO;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWihoutDishes;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWithDishes;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.service.Restaurant.IRestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurants-APIs")
public class RestaurantController {
    @Autowired
    private IRestaurantService restaurantService;

    @Operation(summary = "Get all restaurants", description = "Get detailed information about all restaurants")
    @GetMapping
    public ResponseEntity<List<RestaurantDTOWihoutDishes>> getAllRestaurants() {
        try {
            List<RestaurantDTOWihoutDishes> restaurants = restaurantService.getAllRestaurants();
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get restaurants by cuisine", description = "Get detailed information about restaurants by cuisine")
    @GetMapping(params = "cuisine")
    public ResponseEntity<List<RestaurantDTOWihoutDishes>> getRestaurantsByCuisine(@RequestParam("cuisine") String cuisine) {
        try {
            List<RestaurantDTOWihoutDishes> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTOWithDishes> getRestaurantById(@PathVariable Long id) {
        try {
            RestaurantDTOWithDishes restaurant = restaurantService.getRestaurantById(id);
            return ResponseEntity.ok(restaurant);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addRestaurant(@RequestBody CreateRestaurantDTO createRestaurantDTO) {
        try {
            restaurantService.addRestaurant(createRestaurantDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable Long id, @RequestBody CreateRestaurantDTO createRestaurantDTO) {
        try {
            restaurantService.updateRestaurant(id, createRestaurantDTO);
            return ResponseEntity.ok().build();
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        try {
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
