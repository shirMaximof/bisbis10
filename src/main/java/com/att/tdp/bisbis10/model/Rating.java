package com.att.tdp.bisbis10.model;

import com.att.tdp.bisbis10.DTO.RatingDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ratingId;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private float rating;

    public Rating() {
    }
    public Rating(RatingDTO ratingDTO) {
        this.ratingId = ratingDTO.ratingId();
        this.rating = ratingDTO.rating();
    }
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
