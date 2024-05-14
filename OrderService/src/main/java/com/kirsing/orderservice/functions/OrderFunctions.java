package com.kirsing.orderservice.functions;


import com.kirsing.orderservice.service.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderFunctions {
    private static final Logger log = LogManager.getLogger(OrderFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(OrderServiceImpl orderService) {
        return orderId -> {
            log.info("Updating Communication status for the orderId : " + orderId);
            orderService.updateCommunicationStatus(orderId);
        };
    }
}
