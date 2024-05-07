package com.att.tdp.bisbis10.service.Rating;

import com.att.tdp.bisbis10.DTO.RatingDTO;
import com.att.tdp.bisbis10.exceptions.InvalidRatingException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Rating;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.RatingRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService implements IRatingService{

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, RestaurantRepository restaurantRepository) {
        this.ratingRepository = ratingRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void addRating(RatingDTO ratingDTO) throws RestaurantNotFoundException, InvalidRatingException {
        Rating rating = new Rating(ratingDTO);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(rating.getRestaurantId());
        if (restaurantOptional.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        Restaurant restaurant = restaurantOptional.get();

        if (rating.getRating() < 0 || rating.getRating() > 5) {
            throw new InvalidRatingException("Invalid rating value");
        }

        ratingRepository.save(rating);

        Float avgRating = ratingRepository.findAverageRatingByRestaurantId(rating.getRestaurantId());
        restaurant.setAverageRating(avgRating);
        restaurantRepository.save(restaurant);
    }

}
