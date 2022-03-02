package com.example.productws.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Code cannot be null")
    private String code;

    private String parentCode;

}
