package com.att.tdp.bisbis10.DTO.Restaurant;

import java.util.List;

public record RestaurantDTOWihoutDishes(Long id, String name, Float averageRating, boolean isKosher, List<String> cuisines) {}



