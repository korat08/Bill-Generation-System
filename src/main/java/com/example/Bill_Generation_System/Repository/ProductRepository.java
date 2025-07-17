package com.example.Bill_Generation_System.Repository;


import com.example.Bill_Generation_System.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

   Optional<Product> findByName(String name);

   @Query(value = "select p from Product p where quantityInStock <= lowStockThreshold")
   List<Product> findLowStockProduct();
}
