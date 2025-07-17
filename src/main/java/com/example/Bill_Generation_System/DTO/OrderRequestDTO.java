package com.example.Bill_Generation_System.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Map;

@Data
public class OrderRequestDTO {

    @NotBlank
    private String customerName;

    @NotBlank
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid India phone Number(must be start with 6-9 and be 10 Digits)"
    )
    private String phoneNo;

    @NotNull(message = "Product list cannot be null")
    @Size(min = 1, message = "At least one product must be ordered")
    private Map<String,@Min(1) Integer> listOfItem;
}
