package com.example.Bill_Generation_System.DTO;

import java.math.BigDecimal;

public interface DailyProductSalesReport {
    Integer getProductId();
    String getProductName();
    BigDecimal getProductPrice();
    Integer getQuantitySold();
    BigDecimal getTotalAmount();
}
