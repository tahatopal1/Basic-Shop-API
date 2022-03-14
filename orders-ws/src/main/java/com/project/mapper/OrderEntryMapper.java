package com.project.mapper;

import com.project.dto.OrderEntryDTO;
import com.project.model.OrderEntry;
import org.springframework.stereotype.Component;

@Component
public class OrderEntryMapper implements ModelMapper<OrderEntry, OrderEntryDTO> {
    @Override
    public OrderEntryDTO straightMapping(OrderEntry source) {
        OrderEntryDTO orderEntryDTO = new OrderEntryDTO();
        orderEntryDTO.setCode(source.getCode());
        orderEntryDTO.setName(source.getName());
        orderEntryDTO.setPrice(source.getPrice());
        orderEntryDTO.setStock(source.getCount());
        return orderEntryDTO;
    }

    @Override
    public OrderEntry reverseMapping(OrderEntryDTO destination) {
        OrderEntry orderEntry = new OrderEntry();
        orderEntry.setCode(destination.getCode());
        orderEntry.setName(destination.getName());
        orderEntry.setPrice(destination.getPrice());
        orderEntry.setCount(destination.getStock());
        return orderEntry;
    }
}
