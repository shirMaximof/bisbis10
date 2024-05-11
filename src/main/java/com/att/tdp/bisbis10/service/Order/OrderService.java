package com.att.tdp.bisbis10.service.Order;

import com.att.tdp.bisbis10.DTO.Order.CreateOrderDTO;
import com.att.tdp.bisbis10.DTO.Order.OrderItemDTO;
import com.att.tdp.bisbis10.DTO.Order.OrderResponseDTO;
import com.att.tdp.bisbis10.exceptions.DishNotFoundException;
import com.att.tdp.bisbis10.model.Order;
import com.att.tdp.bisbis10.model.OrderItem;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.repository.OrderRepository;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService implements IOrderService{

    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    @Autowired
    public OrderService(RestaurantRepository restaurantRepository, OrderRepository orderRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    public OrderResponseDTO placeOrder(CreateOrderDTO createOrderDTO) throws RestaurantNotFoundException, DishNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(createOrderDTO.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        List<OrderItem> orderItemList = new LinkedList<>();

        for (OrderItemDTO orderItemDTO : createOrderDTO.orderItems()) {
            dishRepository.findById(orderItemDTO.dishId())
                    .orElseThrow(() -> new DishNotFoundException("Dish not found"));
            orderItemList.add(new OrderItem(orderItemDTO));
        }

        Order order = restaurant.placeOrder(orderItemList);
        orderRepository.save(order);
        return new OrderResponseDTO(order.getOrderId());
    }
}

