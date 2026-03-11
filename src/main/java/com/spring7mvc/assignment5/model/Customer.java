package com.spring7mvc.assignment5.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
@Builder
public class Customer {
    private UUID id;
    private String customerName;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
