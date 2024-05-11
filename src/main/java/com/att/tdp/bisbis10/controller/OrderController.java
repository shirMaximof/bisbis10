package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.OrderDTO;
import com.att.tdp.bisbis10.DTO.OrderResponseDTO;
import com.att.tdp.bisbis10.exceptions.DishNotFoundException;
import com.att.tdp.bisbis10.service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderResponseDTO orderReponseDTO = orderService.placeOrder(orderDTO);
            return ResponseEntity.ok(orderReponseDTO);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DishNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
