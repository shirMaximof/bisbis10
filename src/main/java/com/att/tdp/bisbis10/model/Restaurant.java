package com.att.tdp.bisbis10.model;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float averageRating;
    private boolean isKosher;

    @ElementCollection
    private List<String> cuisines;

    public Restaurant() {}
    public Restaurant(RestaurantDTO restaurantDTO) {
        this.id = restaurantDTO.id();
        this.name = restaurantDTO.name();
        this.isKosher = restaurantDTO.isKosher();
        this.cuisines = restaurantDTO.cuisines();
        this.averageRating = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKosher() {
        return isKosher;
    }

    public void setKosher(boolean kosher) {
        isKosher = kosher;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
}