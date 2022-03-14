package com.project.dto;

import lombok.Data;

@Data
public class OrderEntryDTO {
    private String name;
    private String code;
    private int stock;
    private double price;
}
