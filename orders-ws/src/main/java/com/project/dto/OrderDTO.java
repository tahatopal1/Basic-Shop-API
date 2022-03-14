package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String code;
    private String username;
    private Double totalCost;
    private List<OrderEntryDTO> orderEntries;
}
