package com.example.Bill_Generation_System.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "category_name",nullable = false,unique = true)
    private String categoryName;

    @NotNull
    @DecimalMin(value = "0,0")
    @DecimalMax(value = "100.00")
    @Column(name = "gst_rate",nullable = false)
    private BigDecimal gstRate;
}
