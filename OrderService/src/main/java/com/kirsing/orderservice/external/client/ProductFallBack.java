package com.kirsing.orderservice.external.client;

import com.kirsing.orderservice.external.client.request.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFallBack implements ProductService {
    @Override
    public ResponseEntity<Void> reduceQuantity(long productId, long quantity) {
        return null;
    }
}
