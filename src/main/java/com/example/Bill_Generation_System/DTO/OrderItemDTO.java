package com.example.Bill_Generation_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal gstRate;

    private BigDecimal totalAmount;

    private BigDecimal totalGstAmount;

    private BigDecimal finalAmount;

}
