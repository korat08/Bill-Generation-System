package com.example.Bill_Generation_System.DTO;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be zero or more")
    private Integer quantityInStock;

    @NotNull(message = "Low stock threshold is required")
    @Min(value = 0, message = "Low stock threshold must be zero or more")
    private Integer lowStockThreshold;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
