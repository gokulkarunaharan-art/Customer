package com.spring7mvc.assignment5.controller;

import com.spring7mvc.assignment5.model.Customer;
import com.spring7mvc.assignment5.service.CustomerService;
import com.spring7mvc.assignment5.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
@WebMvcTest
class CustomerControllerTest {

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    MockMvc mockMvc;


    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    public void getCustomerByID() throws Exception {

        Customer testCustomer = customerServiceImpl.getAllCustomers().getFirst();

        given(customerService.getCustomerByID(testCustomer.getId())).willReturn(testCustomer);
        mockMvc.perform(
                get("/api/customer/"+testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testCustomer.getId().toString())));
    }

}