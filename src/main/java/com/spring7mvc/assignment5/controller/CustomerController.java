package com.spring7mvc.assignment5.controller;


import com.spring7mvc.assignment5.exception.CustomerNotFoundException;
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
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH+"/{customerID}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerID") UUID id){
        return customerService.getCustomerByID(id).orElseThrow(CustomerNotFoundException::new);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders header = new HttpHeaders();
        header.add("Location","/api/customer/"+savedCustomer.getId());
        return new ResponseEntity<>(savedCustomer,header, HttpStatus.CREATED);
    }
    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerByID(@PathVariable("customerID") UUID id, @RequestBody Customer customer){
        customerService.updateCustomerByID(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomerByID(@PathVariable("customerID") UUID id){
        customerService.deleteCustomerByID(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerByID(@PathVariable("customerID") UUID id, @RequestBody Customer customer){
        customerService.patchCustomerByID(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
