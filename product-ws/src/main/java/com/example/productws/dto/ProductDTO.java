package com.example.productws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Name cannot be null")
    private String code;

    @NotNull(message = "Stock cannot be null")
    private int stock;

    @NotNull(message = "Price cannot be null")
    private double price;

    @NotNull(message = "Category cannot be null")
    private CategoryDTO categoryDTO;

}
