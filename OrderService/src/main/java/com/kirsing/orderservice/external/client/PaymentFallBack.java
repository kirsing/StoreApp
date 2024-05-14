package com.kirsing.orderservice.external.client;

import com.kirsing.orderservice.external.client.request.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallBack implements PaymentService{
    @Override
    public ResponseEntity<Long> doPayment(PaymentRequest paymentRequest) {
        return null;
    }
}
