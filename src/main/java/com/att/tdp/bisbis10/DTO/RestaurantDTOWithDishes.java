package com.att.tdp.bisbis10.DTO;

import java.util.List;

public record RestaurantDTOWithDishes(Long id, String name, Float averageRating, boolean isKosher, List<String> cuisines, List<DishDTO> dishes) {}
