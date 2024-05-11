package com.att.tdp.bisbis10.service.Order;

import com.att.tdp.bisbis10.DTO.Order.CreateOrderDTO;
import com.att.tdp.bisbis10.DTO.Order.OrderResponseDTO;
import com.att.tdp.bisbis10.exceptions.DishNotFoundException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;

public interface IOrderService {
    OrderResponseDTO placeOrder(CreateOrderDTO createOrderDTO) throws RestaurantNotFoundException, DishNotFoundException;
}
