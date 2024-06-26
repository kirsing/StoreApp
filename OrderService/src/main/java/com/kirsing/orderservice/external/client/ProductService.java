package com.kirsing.orderservice.external.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "productservice/product", url = "http://productservice:8080/product", fallback = ProductFallBack.class)
public interface ProductService {

    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity
    );


//    default ResponseEntity<Void> fallback(Exception e) {
//        throw new CustomException("Product Service is not available",
//                "UNAVAILABLE",
//                500);
//    }

}
