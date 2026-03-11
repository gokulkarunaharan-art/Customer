package com.spring7mvc.assignment5.service;

import com.spring7mvc.assignment5.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Customer getCustomerByID(UUID id);
}
