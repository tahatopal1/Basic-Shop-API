package com.project.mapper;

import com.project.dto.OrderDTO;
import com.project.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper implements ModelMapper<Order, OrderDTO> {

    @Autowired
    private OrderEntryMapper orderEntryMapper;

    @Override
    public OrderDTO straightMapping(Order source) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCode(source.getCode());
        orderDTO.setUsername(source.getUsername());
        orderDTO.setTotalCost(source.getTotalCost());
        orderDTO.setOrderEntries(source.getOrderEntries().stream()
                .map(orderEntryMapper::straightMapping)
                .collect(Collectors.toList()));
        return orderDTO;
    }

    @Override
    public Order reverseMapping(OrderDTO destination) {
        Order order = new Order();
        order.setCode(destination.getCode());
        order.setUsername(destination.getUsername());
        order.setTotalCost(destination.getTotalCost());
        order.setOrderEntries(destination.getOrderEntries().stream()
                .map(orderEntryMapper::reverseMapping)
                .collect(Collectors.toList()));
        return order;
    }
}
