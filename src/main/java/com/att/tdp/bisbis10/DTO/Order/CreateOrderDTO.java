package com.att.tdp.bisbis10.DTO.Order;

import java.util.List;

public record CreateOrderDTO(Long restaurantId, List<OrderItemDTO> orderItems) {}
