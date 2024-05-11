package com.att.tdp.bisbis10.model;

import com.att.tdp.bisbis10.DTO.Restaurant.CreateRestaurantDTO;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWihoutDishes;
import jakarta.persistence.*;

import java.util.LinkedList;
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
    private List<String> cuisines = new LinkedList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Order> orders = new LinkedList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Dish> dishes = new LinkedList<>();

    public Restaurant() {}
    public Restaurant(CreateRestaurantDTO createRestaurantDTO) {
        this.name = createRestaurantDTO.name();
        this.isKosher = createRestaurantDTO.isKosher();
        this.cuisines = createRestaurantDTO.cuisines();
        this.averageRating = -1;
    }
    public Order placeOrder(List<OrderItem> orderItems) {
        Order order = new Order();
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        orders.add(order);
        return order;
    }
    public void addDish(Dish dish) {
        dishes.add(dish);
        dish.setRestaurant(this);
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
        dish.setRestaurant(null);
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

    public List<Dish> getDishes() {
        return dishes;
    }
}