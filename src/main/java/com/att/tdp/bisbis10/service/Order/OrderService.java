package com.att.tdp.bisbis10.service.Order;

import com.att.tdp.bisbis10.DTO.OrderDTO;
import com.att.tdp.bisbis10.DTO.OrderItemDTO;
import com.att.tdp.bisbis10.model.Order;
import com.att.tdp.bisbis10.model.OrderItem;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.OrderRepository;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(RestaurantRepository restaurantRepository, OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    public String placeOrder(OrderDTO orderDTO) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(orderDTO.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        Order order = new Order();
        order.setRestaurantId(restaurant.getId());

        for (OrderItemDTO orderItemDTO : orderDTO.orderItems()) {
            order.addOrderItem(new OrderItem(orderItemDTO));
        }

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getOrderId().toString();
    }
}

