package com.example.Bill_Generation_System.Controller;


import com.example.Bill_Generation_System.CustomResponse.ApiResponse;
import com.example.Bill_Generation_System.DTO.OrderRequestDTO;
import com.example.Bill_Generation_System.DTO.OrderResponseDTO;
import com.example.Bill_Generation_System.Service.OrderService;
import com.example.Bill_Generation_System.Service.WhatsAppService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    WhatsAppService whatsAppSender;

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> placeOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) throws IOException {
        return orderService.placeOrder(orderRequestDTO);
    }

    @PostMapping("/send-a-notification-on-whatsApp")
    public void sendNotification(@RequestBody String message){
        whatsAppSender.sendMessage("+91**********",message);
    }

}
