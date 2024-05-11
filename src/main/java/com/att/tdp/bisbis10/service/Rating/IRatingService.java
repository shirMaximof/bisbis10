package com.att.tdp.bisbis10.service.Rating;

import com.att.tdp.bisbis10.DTO.Rating.CreateRatingDTO;
import com.att.tdp.bisbis10.exceptions.InvalidRatingException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;

public interface IRatingService {
    void addRating(CreateRatingDTO createRatingDTO) throws RestaurantNotFoundException, InvalidRatingException;
}
