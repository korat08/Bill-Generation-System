package com.example.Bill_Generation_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponseDTO {

    private Long orderId;
    private String customerName;
    private String phoneNo;
    private LocalDateTime orderDate;

    private List<OrderItemDTO> items;

    private BigDecimal totalAmount;
    private BigDecimal totalGstAmount;
    private BigDecimal finalAmount;

    public OrderResponseDTO(Long orderId, String customerName, String phoneNo, LocalDateTime orderDate, List<OrderItemDTO> items, BigDecimal totalAmount, BigDecimal totalGstAmount, BigDecimal finalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.orderDate = orderDate;
        this.items = items;
        this.totalAmount = totalAmount;
        this.totalGstAmount = totalGstAmount;
        this.finalAmount = finalAmount;
    }
}
