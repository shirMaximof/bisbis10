package com.att.tdp.bisbis10.DTO;

import java.util.List;
import java.util.UUID;

public record OrderDTO(UUID orderId, Long restaurantId, List<OrderItemDTO> orderItems) {}
