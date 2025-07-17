package com.example.Bill_Generation_System.Controller;

import com.example.Bill_Generation_System.CustomResponse.ApiResponse;
import com.example.Bill_Generation_System.DTO.SignupRequest;
import com.example.Bill_Generation_System.Service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome user";
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse<?>> updateUser(@Valid @RequestBody SignupRequest request, Authentication authentication){
        return customerService.updateUser(request,authentication.getName());
    }

    @GetMapping("/get-profile")
    public ResponseEntity<ApiResponse<?>> getUserDetails(Authentication authentication){
        return customerService.getUserDetails(authentication.getName());
    }

}
