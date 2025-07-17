package com.example.Bill_Generation_System.Service;


import com.example.Bill_Generation_System.CustomResponse.ApiResponse;
import com.example.Bill_Generation_System.DTO.AddProductRequest;
import com.example.Bill_Generation_System.DTO.DailyProductSalesReport;
import com.example.Bill_Generation_System.DTO.UpdateDTO;
import com.example.Bill_Generation_System.Model.Product;
import com.example.Bill_Generation_System.Model.ProductCategory;
import com.example.Bill_Generation_System.Repository.OrderItemRepository;
import com.example.Bill_Generation_System.Repository.ProductCategoryRepository;
import com.example.Bill_Generation_System.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MailService mailService;

    public ResponseEntity<ApiResponse<?>> addProduct(AddProductRequest request) {


        ProductCategory category = productCategoryRepository.findById(request.getCategoryId()).orElse(null);

        if(category == null){
            ApiResponse<String> apiResponse = new ApiResponse<>(false,"Category id not in Category table..",null);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantityInStock(request.getQuantityInStock())
                .lowStockThreshold(request.getLowStockThreshold())
                .category(category)
                .build();

        Product product1 = productRepository.save(product);

        ApiResponse<Product> apiResponse = new ApiResponse<>(true,"Success Inserted",product1);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<?>> deleteProduct(Integer productId) {

        if(!productRepository.existsById(productId)){
            return new ResponseEntity<>(
                    new ApiResponse<>(false,"Invalid Product Id",null),
                    HttpStatus.BAD_REQUEST);
        }

        boolean isUsed = orderItemRepository.existsByProductId(productId);
        if (isUsed) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, "Product is used in existing orders and cannot be deleted", null),
                    HttpStatus.CONFLICT
            );
        }

        try{
            productRepository.deleteById(productId);
            return new ResponseEntity<>(
                    new ApiResponse<>(false,"Deleted SuccessFully...",null),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(
                    new ApiResponse<>(false, "Something went wrong: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ResponseEntity<ApiResponse<?>> getProductById(Integer productId) {

        if(!productRepository.existsById(productId)){
            return new ResponseEntity<>(
                    new ApiResponse<>(false,"Invalid Product Id",null),
                    HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.findById(productId).orElse(null);

        return new ResponseEntity<>(new ApiResponse<>(true,"Success",product),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> getProducts() {

        List<Product> productList = productRepository.findAll();

        return new ResponseEntity<>(new ApiResponse<>(true,"Success",productList),HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<?>> updateProduct(Integer productId, AddProductRequest request) {


        if(!productRepository.existsById(productId)){
            return new ResponseEntity<>(
                    new ApiResponse<>(false,"Invalid Product Id",null),
                    HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.findById(productId).orElse(null);

        if(product == null){
            return new ResponseEntity<>(
                    new ApiResponse<>(false,"Invalid Input",null),
                    HttpStatus.BAD_REQUEST);
        }

        ProductCategory category = productCategoryRepository.findById(request.getCategoryId()).orElse(null);

        if(category == null){
            ApiResponse<String> apiResponse = new ApiResponse<>(false,"Category id not in Category table..",null);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantityInStock(request.getQuantityInStock());
        product.setLowStockThreshold(request.getLowStockThreshold());
        product.setCategory(category);

        productRepository.save(product);

        return new ResponseEntity<>(new ApiResponse<>(true,"Updated SuccessFully",product),HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<?>> updateStock(UpdateDTO updateDTO) {
        Optional<Product> opt = productRepository.findById(updateDTO.getId());
        if(opt.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(false,"Product not Found...",null),HttpStatus.BAD_REQUEST);
        }

        Product product = opt.get();

        product.setQuantityInStock(updateDTO.getQuantity());
        productRepository.save(product);
        return new ResponseEntity<>(new ApiResponse<>(true,"Product Stock update SuccessFully",null),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> createCsvOfDaily() throws IOException {

        List<DailyProductSalesReport> dailyProductSalesReportList = orderItemRepository.fetchTodayProductSales(LocalDate.now());
        CsvGenerator.generateDailySalesReport(dailyProductSalesReportList);

        String to = "aryankorat08@gmail.com";
        String subject = "Today's Sales Report";
        String body = "Hello kirtan,\n" +
                "This email send by Aryan korat to you for Testing Purpose";
        String csvPath = "C:\\Users\\Margish\\Desktop\\Files\\DailySalesReport("+LocalDate.now()+").csv";
        mailService.sendWithCsvAttachment(to,subject,body,csvPath);
        return new ResponseEntity<>(new ApiResponse<>(true,"created successfully",null),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> createCsvOfLowStock() throws IOException {

        List<Product> products = productRepository.findLowStockProduct();
        CsvGenerator.generateLowStockReport(products);

        String to = "aryankorat08@gmail.com";
        String subject = "Today's Low Stock Product Report";
        String body = "Hello kirtan,\n" +
                "This email send by Aryan korat to you for Testing Purpose";
        String csvPath = "C:\\Users\\Margish\\Desktop\\Files\\LowStockReport("+LocalDate.now()+").csv";

        mailService.sendWithCsvAttachment(to,subject,body,csvPath);
        return new ResponseEntity<>(new ApiResponse<>(true,"Send SuccessFully",null),HttpStatus.OK);

    }

}
