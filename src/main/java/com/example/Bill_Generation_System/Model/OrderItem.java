package com.example.Bill_Generation_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal gstRate;
    private BigDecimal totalPrice;
    private BigDecimal TotalGstAmount;
    private BigDecimal finalAmount;

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal unitPrice, BigDecimal gstRate, BigDecimal totalPrice, BigDecimal totalGstAmount, BigDecimal finalAmount) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.gstRate = gstRate;
        this.totalPrice = totalPrice;
        TotalGstAmount = totalGstAmount;
        this.finalAmount = finalAmount;
    }
}

