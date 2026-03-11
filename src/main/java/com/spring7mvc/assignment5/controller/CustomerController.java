package com.spring7mvc.assignment5.controller;


import com.spring7mvc.assignment5.model.Customer;
import com.spring7mvc.assignment5.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders header = new HttpHeaders();
        header.add("Location","/api/customer/"+savedCustomer.getId());
        return new ResponseEntity<>(savedCustomer,header, HttpStatus.CREATED);
    }
    @PutMapping("/{UUID}")
    public ResponseEntity updateCustomerByID(@PathVariable("UUID") UUID id, @RequestBody Customer customer){
        customerService.updateCustomerByID(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
