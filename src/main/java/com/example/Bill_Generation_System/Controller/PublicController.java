package com.example.Bill_Generation_System.Controller;


import com.example.Bill_Generation_System.DTO.SignInRequest;
import com.example.Bill_Generation_System.DTO.SignupRequest;
import com.example.Bill_Generation_System.Service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bill")
public class PublicController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest){
        return customerService.createUser(signupRequest);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> SignIN(@Valid @RequestBody SignInRequest request){
        return customerService.verify(request);
    }
}
