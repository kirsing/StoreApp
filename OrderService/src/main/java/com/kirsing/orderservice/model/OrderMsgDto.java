package com.kirsing.orderservice.model;

import java.time.Instant;

public record OrderMsgDto(Long orderId, Long productId, Long quantity, Instant orderDate, String orderStatus, Long amount) {
}
