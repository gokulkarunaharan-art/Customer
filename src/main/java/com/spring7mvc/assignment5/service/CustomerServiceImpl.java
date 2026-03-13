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
                .customerAddress("Kottar")
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Jane Smith")
                .createdDate(LocalDate.of(2024, 1, 15))
                .lastModifiedDate(LocalDate.of(2024, 6, 20))
                .customerAddress("Nagercoil")
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Bob Johnson")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .customerAddress("Madras")
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
    public Optional<Customer> getCustomerByID(UUID id) {
        return Optional.of(customerList.get(id));
    }

    @Override
    public Customer saveCustomer(Customer customer){
        //manually setting the credentials....
        customer.setId(UUID.randomUUID());
        customer.setCreatedDate(LocalDate.now());
        customer.setLastModifiedDate(LocalDate.now());
        customerList.put(customer.getId(),customer);
        return customer;
    }

    @Override
    public void updateCustomerByID(UUID id, Customer customer){
       Customer existingCustomer =  customerList.get(id);
       existingCustomer.setCustomerName(customer.getCustomerName());
       existingCustomer.setCustomerAddress(customer.getCustomerAddress());
       existingCustomer.setLastModifiedDate(LocalDate.now());
       customerList.put(existingCustomer.getId(),existingCustomer);
    }
    @Override
    public void deleteCustomerByID(UUID id){
        customerList.remove(id);
    }

    @Override
    public void patchCustomerByID(UUID id, Customer customer){
        Customer existingCustomer = customerList.get(id);
        if(customer.getCustomerName() != null){
            existingCustomer.setCustomerName(customer.getCustomerName());
        }
        if(customer.getCustomerAddress() != null){
            existingCustomer.setCustomerAddress(customer.getCustomerAddress());
        }
    }
}
