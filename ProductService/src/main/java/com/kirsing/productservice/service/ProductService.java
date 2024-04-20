package com.kirsing.productservice.service;

import com.kirsing.productservice.model.ProductRequest;
import com.kirsing.productservice.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
