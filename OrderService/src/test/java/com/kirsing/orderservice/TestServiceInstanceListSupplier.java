package com.kirsing.orderservice;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class TestServiceInstanceListSupplier implements ServiceInstanceListSupplier { // for service registry
    @Override
    public String getServiceId() {
        return "";
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        List<ServiceInstance> result =
                new ArrayList<>();
        result.add(new DefaultServiceInstance("PAYMENT-SERVICE",
                                                "PAYMENT-SERVICE",
                                               "localhost",
                                                8080,
                                                false
                ));


        result.add(new DefaultServiceInstance("PAYMENT-SERVICE",
                "PRODUCT-SERVICE",
                "localhost",
                8080,
                false
        ));

        return Flux.just(result);
    }
}
