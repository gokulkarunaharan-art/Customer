package com.spring7mvc.assignment5.service;

import com.spring7mvc.assignment5.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
     List<Customer> getAllCustomers();
     Customer getCustomerByID(UUID id);
     Customer saveCustomer(Customer customer);
     void updateCustomerByID(UUID id, Customer customer);

}
