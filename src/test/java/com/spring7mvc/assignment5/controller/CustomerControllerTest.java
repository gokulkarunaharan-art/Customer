package com.spring7mvc.assignment5.controller;
import com.spring7mvc.assignment5.exception.CustomerNotFoundException;
import com.spring7mvc.assignment5.model.Customer;
import com.spring7mvc.assignment5.service.CustomerService;
import com.spring7mvc.assignment5.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

import static com.spring7mvc.assignment5.controller.CustomerController.CUSTOMER_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest
class CustomerControllerTest {

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

     @Captor
     ArgumentCaptor<UUID> uuidCaptor = ArgumentCaptor.forClass(UUID.class);

     @Captor
     ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);


    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    public void getCustomerByID() throws Exception {

        Customer testCustomer = customerServiceImpl.getAllCustomers().getFirst();

        given(customerService.getCustomerByID(testCustomer.getId())).willReturn(Optional.of(testCustomer));
        mockMvc.perform(
                get(CUSTOMER_PATH+"/"+testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testCustomer.getId().toString())));
    }

    @Test
    public void getAllCustomers() throws Exception {

        List<Customer> customers = customerServiceImpl.getAllCustomers();

        given(customerService.getAllCustomers()).willReturn(customers);
        mockMvc.perform(
                        get(CUSTOMER_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    public void saveCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().getFirst();
        String json = objectMapper.writeValueAsString(testCustomer);
        testCustomer.setId(null);
        testCustomer.setCreatedDate(null);
        testCustomer.setLastModifiedDate(null);

        given(customerService.saveCustomer(any(Customer.class)))
                .willReturn(customerServiceImpl.getAllCustomers().get(1));
        mockMvc.perform(
                post(CUSTOMER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(
                status().isCreated()
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"));

    }

    @Test
    public void updateCustomerByID() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().getFirst();

        String json = objectMapper.writeValueAsString(testCustomer);

        mockMvc.perform(
                put(CUSTOMER_PATH+"/"+testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isNoContent());
        verify(customerService).updateCustomerByID(any(UUID.class),any(Customer.class));

    }

    @Test
    public void deleteCustomerByID() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().getFirst();

        mockMvc.perform(
                        delete(CUSTOMER_PATH+"/" + testCustomer.getId())

                )
                .andExpect(status().isNoContent());
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(customerService).deleteCustomerByID(argumentCaptor.capture());
        assertThat(testCustomer.getId()).isEqualTo(argumentCaptor.getValue());

    }

    @Test
    public void patchBeerByID() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().getFirst();
        Map<String, Object> json = new HashMap<>();
        json.put("customerName", testCustomer.getCustomerName());

        mockMvc.perform(
                patch(CUSTOMER_PATH+"/" + testCustomer.getId())
                        .content(objectMapper.writeValueAsString(json))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(customerService).patchCustomerByID(uuidCaptor.capture(), customerCaptor.capture());
        assertThat(testCustomer.getId()).isEqualTo(uuidCaptor.getValue());
        assertThat(testCustomer.getCustomerName()).isEqualTo(customerCaptor.getValue().getCustomerName());
    }

    @Test
    public void customerNotFoundExceptionTest() throws Exception {

        given(customerService.getCustomerByID(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(
                get(CUSTOMER_PATH + "/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }
}