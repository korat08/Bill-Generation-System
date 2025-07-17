package com.example.Bill_Generation_System.Service;

import com.example.Bill_Generation_System.CustomResponse.ApiResponse;
import com.example.Bill_Generation_System.DTO.SignInRequest;
import com.example.Bill_Generation_System.DTO.SignupRequest;
import com.example.Bill_Generation_System.Model.Customer;
import com.example.Bill_Generation_System.Model.CustomerDetails;
import com.example.Bill_Generation_System.Model.Role;
import com.example.Bill_Generation_System.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> createUser(SignupRequest request) {

        if (customerRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity<>(Map.of("message","Email already exists"),HttpStatus.BAD_REQUEST);
        }else if (customerRepository.existsByPhoneNo(request.getPhoneNumber())) {
            return new ResponseEntity<>(Map.of("message","Phone Number already exists"),HttpStatus.BAD_REQUEST);
        } else if (customerRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(Map.of("message","Name already exists"),HttpStatus.BAD_REQUEST);
        }

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNo(request.getPhoneNumber())
                .city(request.getCity())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        customerRepository.save(customer);

        return new ResponseEntity<>(Map.of("Message :","User created SuccessFully.."), HttpStatus.CREATED);
    }

    public ResponseEntity<?> verify(SignInRequest request) {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getName(),request.getPassword()));


            CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();

            String token = jwtService.getToken(request.getName(),customerDetails.getCustomer().getRole());

            return new ResponseEntity<>(Map.of("token :",token),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(Map.of("Message :",e.getMessage()),HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ApiResponse<?>> updateUser(SignupRequest request, String name) {

        Customer customer = customerRepository.findByName(name);

        if (customer == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found", null), HttpStatus.NOT_FOUND);
        }

        Customer emailOwner = customerRepository.findByEmail(request.getEmail());
        if (emailOwner != null && !emailOwner.getId().equals(customer.getId())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Email already exists", null), HttpStatus.BAD_REQUEST);
        }

        Customer phoneOwner = customerRepository.findByPhoneNo(request.getPhoneNumber());
        if (phoneOwner != null && !phoneOwner.getId().equals(customer.getId())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Phone Number already exists", null), HttpStatus.BAD_REQUEST);
        }

        Customer nameOwner = customerRepository.findByName(request.getName());
        if (nameOwner != null && !nameOwner.getId().equals(customer.getId())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Name already exists", null), HttpStatus.BAD_REQUEST);
        }


        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setCity(request.getCity());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setPhoneNo(request.getPhoneNumber());

        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Failed to update user: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(new ApiResponse<>(true,"Updated SuccessFully",customer),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> getUserDetails(String name) {
        Customer customer = customerRepository.findByName(name);

        return new ResponseEntity<>(new ApiResponse<>(true,"Fetched",customer),HttpStatus.OK);
    }
}
