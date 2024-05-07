package com.att.tdp.bisbis10.DTO;

import java.util.List;

public record OrderDTO(Long orderId, Long restaurantId, List<OrderItemDTO> orderItems) {}
