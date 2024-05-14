package com.kirsing.orderservice.service;

import com.kirsing.orderservice.model.OrderRequest;
import com.kirsing.orderservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);

    boolean updateCommunicationStatus(Long orderId);
}
