package com.kirsing.message.dto;


import java.time.Instant;

public record OrderMsgDto(Long orderId,Long productId, Long quantity, Instant orderDate, String orderStatus, Long amount) {
}
