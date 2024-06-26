package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.Dish.CreateDishDTO;
import com.att.tdp.bisbis10.DTO.Dish.DishDTO;
import com.att.tdp.bisbis10.exceptions.DishNotFoundException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.service.Dish.IDishService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{id}/dishes")
@Tag(name = "Dishes-APIs")
public class DishController {

    @Autowired
    private IDishService dishService;

    @PostMapping
    public ResponseEntity<Void> addDish(@PathVariable Long id, @RequestBody CreateDishDTO createDishDTO) {
        try {
            dishService.addDish(id, createDishDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @PathVariable Long dishId, CreateDishDTO createDishDTO) {
        try {
            dishService.updateDish(id, dishId, createDishDTO);
            return ResponseEntity.ok().build();
        } catch (DishNotFoundException | RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id, @PathVariable Long dishId) {
        try {
            dishService.deleteDish(id, dishId);
            return ResponseEntity.noContent().build();
        } catch (DishNotFoundException | RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getDishesByRestaurantId(@PathVariable Long id) {
        try {
            List<DishDTO> dishes = dishService.getDishesByRestaurantId(id);
            return ResponseEntity.ok(dishes);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
