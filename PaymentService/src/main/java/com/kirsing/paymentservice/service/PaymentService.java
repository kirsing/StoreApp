package com.kirsing.paymentservice.service;

import com.kirsing.paymentservice.model.PaymentRequest;
import com.kirsing.paymentservice.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
