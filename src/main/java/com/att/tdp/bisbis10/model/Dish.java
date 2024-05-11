package com.att.tdp.bisbis10.model;

import com.att.tdp.bisbis10.DTO.Dish.CreateDishDTO;
import com.att.tdp.bisbis10.DTO.Dish.DishDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double price;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    public Dish(CreateDishDTO createDishDTO) {
        this.name = createDishDTO.name();
        this.description = createDishDTO.description();
        this.price = createDishDTO.price();
    }

    public Dish() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}