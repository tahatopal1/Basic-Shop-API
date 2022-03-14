package com.project.service;

import com.project.dto.OrderDTO;
import com.project.dto.OrderEntryDTO;

import java.util.List;

public interface OrderService {

    boolean controlOrderEntries(List<OrderEntryDTO> orderEntries);

    OrderDTO placeOrder(OrderDTO orderDTO);

    OrderDTO getOrder(String code) throws Exception;

    List<OrderDTO> getOrders(String username);

    void cancelOrder(String code) throws Exception;

}
