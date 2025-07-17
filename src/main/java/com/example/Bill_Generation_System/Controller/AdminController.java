package com.example.Bill_Generation_System.Controller;


import com.example.Bill_Generation_System.CustomResponse.ApiResponse;
import com.example.Bill_Generation_System.DTO.AddProductRequest;
import com.example.Bill_Generation_System.DTO.UpdateDTO;
import com.example.Bill_Generation_System.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Autowired
    ProductService productService;


    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome admin";
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse<?>> addProduct(@Valid @RequestBody AddProductRequest request){
        return productService.addProduct(request);
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }

    @GetMapping("/get-product/{productId}")
    public ResponseEntity<ApiResponse<?>> getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }

    @GetMapping("/get-products")
    public ResponseEntity<ApiResponse<?>> getProducts(){
        return productService.getProducts();
    }

    @PutMapping("/update-product-stock")
    public ResponseEntity<ApiResponse<?>> updateStock(@Valid @RequestBody UpdateDTO updateDTO){
        return productService.updateStock(updateDTO);
    }

    @PutMapping("/update-product/{productId}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable Integer productId,@Valid @RequestBody AddProductRequest request){
        return productService.updateProduct(productId,request);
    }

    @GetMapping("/create-csv-of-dailySales-report")
    public ResponseEntity<ApiResponse<?>> createCsvOfDaily() throws IOException {
        return productService.createCsvOfDaily();
    }

    @GetMapping("/create-csv-of-lowStock-report")
    public ResponseEntity<ApiResponse<?>> createCsvOfLowStock() throws IOException {
        return productService.createCsvOfLowStock();
    }

}
