package com.example.Bill_Generation_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> itemList = new ArrayList<>();

    private BigDecimal totalAmount;

    private BigDecimal totalGstAmount;

    private BigDecimal finalAmount;

    public Order(Customer customer, List<OrderItem> itemList, BigDecimal totalAmount, BigDecimal totalGst, BigDecimal finalAmount) {
        this.customer = customer;
        this.itemList = itemList;
        this.totalAmount = totalAmount;
        this.totalGstAmount = totalGst;
        this.finalAmount = finalAmount;
    }
}
