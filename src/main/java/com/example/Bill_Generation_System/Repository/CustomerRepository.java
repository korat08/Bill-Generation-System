package com.example.Bill_Generation_System.Repository;


import com.example.Bill_Generation_System.Model.Customer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    Boolean existsByName(String name);

    Boolean existsByPhoneNo(String phoneNo);

    Customer findByPhoneNo(String phone);

    Customer findByName(String username);

    boolean existsByEmail(String email);

    Customer findByEmail(@NotBlank String email);
}
