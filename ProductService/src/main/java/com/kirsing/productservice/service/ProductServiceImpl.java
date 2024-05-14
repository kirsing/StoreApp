package com.kirsing.productservice.service;

import com.kirsing.productservice.entity.Product;
import com.kirsing.productservice.exception.InsufficientException;
import com.kirsing.productservice.exception.ResourceNotFoundException;
import com.kirsing.productservice.model.ProductRequest;
import com.kirsing.productservice.model.ProductResponse;
import com.kirsing.productservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");

        Product product
                = Product.builder().productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product added successfully");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productId: {}", productId);

        Product product = productRepository.findById(productId) // new ResourceNotFoundException("Order", "id", Long.toString(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", Long.toString(productId)));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
       log.info("Reduce Quantity {} for Id: {}", quantity, productId);

       Product product = productRepository.findById(productId)
               .orElseThrow(() -> new ResourceNotFoundException("Product", "id", Long.toString(productId)));

       if(product.getQuantity() < quantity) {
           throw new InsufficientException(getProductById(1).getProductName(), "quantity", Long.toString(quantity));
           //ProductServiceCustomException(
                   //"Product doesn't have sufficient Quantity",
                   //"INSUFFICIENT_QUANTITY");
       }
       product.setQuantity(product.getQuantity() - quantity);
       productRepository.save(product);
       log.info("Product Quantity updated Successfully");
    }
}
