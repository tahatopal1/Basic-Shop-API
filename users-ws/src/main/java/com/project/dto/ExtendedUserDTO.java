package com.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExtendedUserDTO extends UserDTO {
    private List<ProductDTO> suggested = new ArrayList<>();
}
