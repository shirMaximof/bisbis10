package com.att.tdp.bisbis10.DTO;

import java.util.List;

public record RestaurantDTO(Long id, String name, boolean isKosher,float averageRating, List<String> cuisines) {
}

