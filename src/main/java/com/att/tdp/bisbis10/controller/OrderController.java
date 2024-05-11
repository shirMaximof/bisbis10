package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.Order.CreateOrderDTO;
import com.att.tdp.bisbis10.DTO.Order.OrderResponseDTO;
import com.att.tdp.bisbis10.exceptions.DishNotFoundException;
import com.att.tdp.bisbis10.service.Order.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/order")
@Tag(name = "Orders-APIs")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        try {
            OrderResponseDTO orderReponseDTO = orderService.placeOrder(createOrderDTO);
            return ResponseEntity.ok(orderReponseDTO);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DishNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
