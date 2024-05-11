package com.att.tdp.bisbis10.DTO.Restaurant;

import java.util.List;

public record CreateRestaurantDTO(String name, boolean isKosher, List<String> cuisines) {}