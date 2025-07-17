package com.example.Bill_Generation_System.Service;


import com.example.Bill_Generation_System.CustomResponse.ApiResponse;
import com.example.Bill_Generation_System.DTO.*;
import com.example.Bill_Generation_System.Model.Customer;
import com.example.Bill_Generation_System.Model.Order;
import com.example.Bill_Generation_System.Model.OrderItem;
import com.example.Bill_Generation_System.Model.Product;
import com.example.Bill_Generation_System.Repository.CustomerRepository;
import com.example.Bill_Generation_System.Repository.OrderItemRepository;
import com.example.Bill_Generation_System.Repository.OrderRepository;
import com.example.Bill_Generation_System.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class OrderService {

    @Value("${admin.phone}")
    private String adminPhone;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WhatsAppService whatsAppSender;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MailService mailService;

    @Autowired
    SmsService smsService;

    @Transactional
    public ResponseEntity<ApiResponse<OrderResponseDTO>> placeOrder(OrderRequestDTO orderRequestDTO) throws IOException {

        //=====================================================================================
        // task 1 :- First check customer exist or not by

        if(!customerRepository.existsByName(orderRequestDTO.getCustomerName()) || !customerRepository.existsByPhoneNo(orderRequestDTO.getPhoneNo())){
            ApiResponse<OrderResponseDTO> response = new ApiResponse<>(false,"Customer name or Phone number not Found",null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        //=====================================================================================
        // task 2 :- iterate all List item of request to check exist or not and calculate a billing

        Customer customer = customerRepository.findByPhoneNo(orderRequestDTO.getPhoneNo());

        // for database store
        Order order  = new Order();
        List<OrderItem> itemList = new ArrayList<>();

        // for client response
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        List<OrderItemDTO> itemDTOList = new ArrayList<>();

        // for total bills amount;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalGstAmount = BigDecimal.ZERO;
        BigDecimal finalAmount = BigDecimal.ZERO;


        for(Map.Entry<String,Integer> entry:orderRequestDTO.getListOfItem().entrySet()){

            //get name and quantity one bye one
            String productName = entry.getKey();
            Integer quantity = entry.getValue();

            // check product is exist or not
            Optional<Product> productOpt = productRepository.findByName(productName);

            if(productOpt.isEmpty()){
                throw new RuntimeException("Product not found");
            }

            // check product quantity
            Product product = productOpt.get();

            if(product.getQuantityInStock() < quantity){
                throw new RuntimeException("Product not in stock");
            }

            // calculating gst

            // it is for one product
            BigDecimal totalAmountOfProduct = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            BigDecimal totalGstAmountOfProduct = product.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(product.getCategory().getGstRate()).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
            BigDecimal finalAmountOfProduct = totalAmountOfProduct.add(totalGstAmountOfProduct);


            // it is for multiple product
            totalAmount = totalAmount.add(totalAmountOfProduct);
            totalGstAmount = totalGstAmount.add(totalGstAmountOfProduct);
            finalAmount = finalAmount.add(finalAmountOfProduct);

            // update product stock
            product.setQuantityInStock(product.getQuantityInStock()-quantity);
            productRepository.save(product);


            // if stock trigger threshold than send notification to admin

            if(product.getQuantityInStock() < product.getLowStockThreshold()){

                int currentStock = product.getQuantityInStock();
                int threshold = product.getLowStockThreshold();

                String message = "⚠️ STOCK ALERT:\n\nProduct: " + productName +
                        "\nCurrent Stock: " + currentStock +
                        "\nThreshold Limit: " + threshold +
                        "\n\nPlease restock soon to avoid out-of-stock issues.";
                whatsAppSender.sendMessage(adminPhone,message);
            }


            //for database store
            OrderItem orderItem = new OrderItem(order,product,quantity,product.getPrice(),
                    product.getCategory().getGstRate(),totalAmountOfProduct,totalGstAmountOfProduct,finalAmountOfProduct);

            //for client response
            OrderItemDTO orderItemDTO = new OrderItemDTO(productName,quantity,product.getPrice(),
                    product.getCategory().getGstRate(),totalAmountOfProduct,totalGstAmountOfProduct,finalAmountOfProduct);

            itemList.add(orderItem);
            itemDTOList.add(orderItemDTO);

        }


        //===================================================================================

        // Task :- check payment status
        boolean paymentStatus = true;

        if(!paymentStatus){
            String customerPhone = "91"+orderRequestDTO.getPhoneNo();
            String failureMessage = "❌ Payment failed. Your order has not been processed.\nPlease try again or contact support.";

            whatsAppSender.sendMessage(customerPhone, failureMessage);

            throw  new RuntimeException("Payment failed. Rolling back transaction.");
        }else{
            String customerPhone = "91"+orderRequestDTO.getPhoneNo();
            String customerMessage = "✅ Payment received. Your order is confirmed!\nTotal: ₹" + finalAmount;
            whatsAppSender.sendMessage(customerPhone, customerMessage);
        }



        // add to into order data table
        order.setCustomer(customer);
        order.setItemList(itemList);
        order.setTotalAmount(totalAmount);
        order.setTotalGstAmount(totalGstAmount);
        order.setFinalAmount(finalAmount);

        orderRepository.save(order);

        // set a DTO for client
        orderResponseDTO.setOrderId(order.getId());
        orderResponseDTO.setCustomerName(customer.getName());
        orderResponseDTO.setPhoneNo(customer.getPhoneNo());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        orderResponseDTO.setItems(itemDTOList);
        orderResponseDTO.setTotalAmount(totalAmount);
        orderResponseDTO.setTotalGstAmount(totalGstAmount);
        orderResponseDTO.setFinalAmount(finalAmount);


        // send a notification to Customer for successfully all done on WhatsApp.

        String customerPhone = "91"+orderRequestDTO.getPhoneNo();
        String customerMessage =
                "✅ *Order Confirmed!*\n\nThank you for your purchase, " + customer.getName() +
                        "!\nOrder ID: " + order.getId() +
                        "\nTotal: ₹" + finalAmount +
                        "\n\nWe will notify you once your order is shipped.";
        whatsAppSender.sendMessage(customerPhone, customerMessage);


        // send a notification to Customer for successfully all done on Message.

        String to = "+91**********";
        String message =
                "✅ *Order Confirmed!*\n\nThank you for your purchase, " + customer.getName() +
                        "!\nOrder ID: " + order.getId() +
                        "\nTotal: ₹" + finalAmount +
                        "\n\nWe will notify you once your order is shipped.";
        smsService.sendSms(to,message);


        // response to the client
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>(true,"Bill Genarated Successfully.",orderResponseDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
