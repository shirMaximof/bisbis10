package com.att.tdp.bisbis10;

import com.att.tdp.bisbis10.DTO.Order.CreateOrderDTO;
import com.att.tdp.bisbis10.DTO.Order.OrderItemDTO;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Dish;
import com.att.tdp.bisbis10.model.Order;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.repository.OrderRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.att.tdp.bisbis10.service.Order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private OrderService orderService;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        Long dishId1 = 2L;
        Long dishId2 = 3L;
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(1L, Arrays.asList(new OrderItemDTO(dishId1, 2), new OrderItemDTO(dishId2, 1)));
        Restaurant restaurant = new Restaurant();
        Dish dish = new Dish();

        when(restaurantRepository.findById(createOrderDTO.restaurantId())).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(dishId1)).thenReturn(Optional.of(dish));
        when(dishRepository.findById(dishId2)).thenReturn(Optional.of(dish));

        // Act + assert
        try
        {
             orderService.placeOrder(createOrderDTO);
            verify(orderRepository, times(1)).save(any(Order.class));
        }
        catch (Exception e)
        {
          fail(e);
        }
    }

    @Test
    void testPlaceOrder_RestaurantNotFound() {
        // Arrange
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(1L, Arrays.asList(new OrderItemDTO(1L, 2), new OrderItemDTO(2L, 1)));
        when(restaurantRepository.findById(createOrderDTO.restaurantId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> orderService.placeOrder(createOrderDTO));
        verifyNoInteractions(orderRepository);
    }
}

