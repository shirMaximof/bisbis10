package com.att.tdp.bisbis10.model;

import com.att.tdp.bisbis10.DTO.RatingDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ratingId;
    private long restaurantId;
    private float rating;

    public Rating() {
    }
    public Rating(RatingDTO ratingDTO) {
        this.ratingId = ratingDTO.ratingId();
        this.restaurantId = ratingDTO.restaurantId();
        this.rating = ratingDTO.rating();
    }

    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}