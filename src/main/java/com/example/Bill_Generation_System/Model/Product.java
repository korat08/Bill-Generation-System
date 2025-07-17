package com.example.Bill_Generation_System.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false,unique = true)
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be Greater than zero")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    @Min(value = 0)
    private Integer quantityInStock;

    @NotNull
    @Column(nullable = false)
    @Min(value = 1,message = "Threshold value must be at least 1")
    private Integer lowStockThreshold;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private ProductCategory category;
}
