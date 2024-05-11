package com.att.tdp.bisbis10.service.Rating;

import com.att.tdp.bisbis10.DTO.Rating.CreateRatingDTO;
import com.att.tdp.bisbis10.exceptions.InvalidRatingException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Rating;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.RatingRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements IRatingService{

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, RestaurantRepository restaurantRepository) {
        this.ratingRepository = ratingRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void addRating(CreateRatingDTO createRatingDTO) throws RestaurantNotFoundException, InvalidRatingException {
        Restaurant restaurant = restaurantRepository.findById(createRatingDTO.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + createRatingDTO.restaurantId()));

        Rating rating = new Rating(createRatingDTO);

        if (rating.getRating() < 0 || rating.getRating() > 5) {
            throw new InvalidRatingException("Invalid rating value");
        }

        rating.setRestaurant(restaurant);
        ratingRepository.save(rating);

        Float avgRating = ratingRepository.findAverageRatingByRestaurantId(createRatingDTO.restaurantId());
        restaurant.setAverageRating(avgRating);

        restaurantRepository.save(restaurant);
    }

}
