package com.kirsing.orderservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Order",
        description = "Schema to hold Order information"
)
public class OrderRequest {
    @Positive(message = "productId must be > 0")
    private long productId;
    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private long totalAmount;
    @Positive(message = "Quantity should be greater than zero")
    private long quantity;
    private PaymentMode paymentMode;
}
