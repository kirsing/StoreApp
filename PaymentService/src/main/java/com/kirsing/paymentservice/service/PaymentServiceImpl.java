package com.kirsing.paymentservice.service;

import com.kirsing.paymentservice.entity.TransactionDetails;
import com.kirsing.paymentservice.model.PaymentMode;
import com.kirsing.paymentservice.model.PaymentRequest;
import com.kirsing.paymentservice.model.PaymentResponse;
import com.kirsing.paymentservice.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails =
                TransactionDetails.builder()
                        .paymentDate(Instant.now())
                        .paymentMode(paymentRequest.getPaymentMode().name())
                        .paymentStatus("SUCCESS")
                        .orderId(paymentRequest.getOrderId())
                        .referenceNumber(paymentRequest.getReferenceNumber())
                        .amount(paymentRequest.getAmount())
                        .build();

        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction Completed with ID: {}", transactionDetails);
    return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting Payment Details by Order ID: {}", orderId);
        TransactionDetails transactionDetails =
                transactionDetailsRepository.findByOrderId(Long.valueOf(orderId));

        PaymentResponse paymentResponse =
                PaymentResponse.builder()
                        .paymentId(transactionDetails.getId())
                        .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                        .paymentDate(transactionDetails.getPaymentDate())
                        .orderId(transactionDetails.getOrderId())
                        .status(transactionDetails.getPaymentStatus())
                        .amount(transactionDetails.getAmount())
                        .build();

        return paymentResponse;
    }
}
