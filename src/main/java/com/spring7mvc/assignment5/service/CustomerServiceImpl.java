package com.spring7mvc.assignment5.service;

import com.spring7mvc.assignment5.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customerList;
    public CustomerServiceImpl(){
        this.customerList = new HashMap<>();
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John Doe")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Jane Smith")
                .createdDate(LocalDate.of(2024, 1, 15))
                .lastModifiedDate(LocalDate.of(2024, 6, 20))
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Bob Johnson")
                .createdDate(LocalDate.of(2023, 5, 10))
                .lastModifiedDate(LocalDate.of(2024, 3, 8))
                .build();
        customerList.put(customer1.getId(), customer1);
        customerList.put(customer2.getId(), customer2);
        customerList.put(customer3.getId(), customer3);

    }
    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerList.values());
    }

    @Override
    public Customer getCustomerByID(UUID id) {
        return customerList.get(id);
    }
}
