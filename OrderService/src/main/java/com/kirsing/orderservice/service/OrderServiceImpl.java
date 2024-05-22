package com.kirsing.orderservice.service;

import com.kirsing.orderservice.entity.Order;
import com.kirsing.orderservice.exception.ResourceNotFoundException;
import com.kirsing.orderservice.external.client.PaymentService;

import com.kirsing.orderservice.external.client.ProductService;
import com.kirsing.orderservice.external.client.request.PaymentRequest;
import com.kirsing.orderservice.external.client.response.PaymentResponse;
import com.kirsing.orderservice.external.client.response.ProductResponse;
import com.kirsing.orderservice.model.OrderMsgDto;
import com.kirsing.orderservice.model.OrderRequest;
import com.kirsing.orderservice.model.OrderResponse;
import com.kirsing.orderservice.repository.OrderRepository;


import lombok.AllArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private static final Logger log = LogManager.getLogger(OrderServiceImpl.class);


private final StreamBridge streamBridge;



    private OrderRepository orderRepository;


    private ProductService productService;


    private PaymentService paymentService;


    private RestTemplate restTemplate;



    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success-> COMPLETE, Else
        //CANCELLED

        log.info("Placing Order Request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest
                = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        sendCommunication(order);

        log.info("Order Places successfully with Order Id: {}", order.getId());
        return order.getId();
    }
    private void sendCommunication(Order order) {
        final StreamBridge streamBridge = this.streamBridge;
        var ordersMsgDto = new OrderMsgDto(order.getId(), order.getProductId(), order.getQuantity(), order.getOrderDate(),
                order.getOrderStatus(), order.getAmount());
        log.info("Sending Communication request for the details: {}", ordersMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", ordersMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }
    @Override
    public boolean updateCommunicationStatus(Long orderId) {
        boolean isUpdated = false;
        if(orderId != 0){
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id", Long.toString(orderId))
            );
            order.setCommunicationSw(true);
            orderRepository.save(order);
            isUpdated = true;
        }
        return  isUpdated;
    }


    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for Order Id : {}", orderId);

        Order order
                = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", Long.toString(orderId)));

        log.info("Invoking Product service to fetch the product for id: {}", order.getProductId());
        ProductResponse productResponse
                = restTemplate.getForObject(
                "http://productservice:8080/product/" + order.getProductId(),
                ProductResponse.class   // "http://PRODUCTSERVICE/product/"
        );

        log.info("Getting payment information form the payment Service");
        PaymentResponse paymentResponse
                = restTemplate.getForObject(
                "http://paymentservice:9000/payment/"+ "order/" + order.getId(),
                PaymentResponse.class  //"http://PAYMENTSERVICE/payment/order/"
        );

        OrderResponse.ProductDetails productDetails
                = OrderResponse.ProductDetails
                .builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

            OrderResponse.PaymentDetails paymentDetails
                    = OrderResponse.PaymentDetails
                    .builder()
                    .paymentId(paymentResponse.getPaymentId())
                    .paymentStatus(paymentResponse.getStatus())
                    .paymentDate(paymentResponse.getPaymentDate())
                    .paymentMode(paymentResponse.getPaymentMode())
                    .build();


        OrderResponse orderResponse
                = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        return orderResponse;
    }
}
