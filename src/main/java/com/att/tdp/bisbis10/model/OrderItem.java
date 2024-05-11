package com.att.tdp.bisbis10.model;

import com.att.tdp.bisbis10.DTO.Order.OrderItemDTO;
import jakarta.persistence.*;


@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long dishId;
    private int amount;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    public OrderItem(OrderItemDTO orderItemDTO) {
        this.dishId = orderItemDTO.dishId();
        this.amount = orderItemDTO.amount();
    }

    public OrderItem() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
