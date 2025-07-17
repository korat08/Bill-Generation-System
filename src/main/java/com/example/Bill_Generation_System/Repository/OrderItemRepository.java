package com.example.Bill_Generation_System.Repository;



import com.example.Bill_Generation_System.DTO.DailyProductSalesReport;
import com.example.Bill_Generation_System.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = """
    SELECT 
        p.id AS productId,
        p.name AS productName,
        p.price AS productPrice,
        SUM(oi.quantity) AS quantitySold,
        SUM(oi.unit_price * oi.quantity) AS totalAmount
    FROM 
        order_items oi
    JOIN 
        products p ON oi.product_id = p.id
    JOIN 
        orders o ON oi.order_id = o.id
    WHERE 
        DATE(o.order_date) = :today
    GROUP BY 
        p.id, p.name, p.price
    """, nativeQuery = true)

    List<DailyProductSalesReport> fetchTodayProductSales(@Param("today") LocalDate today);


    boolean existsByProductId(Integer productId);
}
