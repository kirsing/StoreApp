package com.kirsing.productservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Products",
        description = "Schema to hold Product information"
)
@Data
public class ProductRequest {
    @NotEmpty(message = "name can not be a null or empty and contains at least 2 symbols but not more 10")
    @Size(min=2, max=10)
    private String name;

    @Positive(message = "Price should be greater than zero")
    private long price;

    @PositiveOrZero
    private long quantity;
}
