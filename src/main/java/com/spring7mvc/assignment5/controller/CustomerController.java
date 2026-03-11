package com.spring7mvc.assignment5.controller;


import com.spring7mvc.assignment5.model.Customer;
import com.spring7mvc.assignment5.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{UUID}")
    public Customer getCustomerById(@PathVariable("UUID") UUID id){
        return customerService.getCustomerByID(id);
    }

    @GetMapping()
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
}
