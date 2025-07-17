package com.example.Bill_Generation_System.Service;

import com.example.Bill_Generation_System.Model.Customer;
import com.example.Bill_Generation_System.Model.CustomerDetails;
import com.example.Bill_Generation_System.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByName(username);

        if(customer == null){
            throw new UsernameNotFoundException("User Not Found");
        }

        return new CustomerDetails(customer);
    }
}
