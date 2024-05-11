package com.att.tdp.bisbis10.DTO;

import java.util.List;

public record RestaurantDTO(Long id, String name, Float averageRating, boolean isKosher, List<String> cuisines) {}



