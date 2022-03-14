package com.project.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.client.ProductClient;
import com.project.dto.OrderDTO;
import com.project.dto.OrderEntryDTO;
import com.project.dto.ProductDTO;
import com.project.mapper.OrderMapper;
import com.project.model.Order;
import com.project.model.OrderEntry;
import com.project.repository.OrderEntryRepository;
import com.project.repository.OrderRepository;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductClient productClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean controlOrderEntries(List<OrderEntryDTO> orderEntries) {
        return orderEntries.stream().noneMatch(orderEntry -> {
            Response response = productClient.getProduct(orderEntry.getCode());
            ProductDTO productDTO = null;
            try {
                productDTO = readProductDTO(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return orderEntry.getStock() > productDTO.getStock();
        });
    }

    @Override
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        Order order = orderMapper.reverseMapping(orderDTO);
        boolean allClear = true;
        for (OrderEntry oe: order.getOrderEntries()){
            ResponseEntity exchange = productClient.exchange(oe.getCode(), oe.getCount());
            if (exchange.getStatusCode() != HttpStatus.OK){
                allClear = false;
                break;
            }
        }
        if (allClear){
            AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
            order.setId(UUID.randomUUID().toString());
            order.setCode(UUID.randomUUID().toString().substring(0,8));
            order.getOrderEntries().forEach(orderEntry -> {
                orderEntry.setId(UUID.randomUUID().toString());
                totalCost.updateAndGet(v -> v + orderEntry.getPrice() * orderEntry.getCount());
            });
            order.setTotalCost(totalCost.get());
            orderRepository.save(order);
            orderDTO.setTotalCost(totalCost.get());
            return orderDTO;
        }else return new OrderDTO();
    }

    @Override
    public OrderDTO getOrder(String code) throws Exception {
        Order order = Optional.ofNullable(orderRepository.findByCode(code)).orElseThrow(() -> new Exception("Order is not found"));
        return orderMapper.straightMapping(order);
    }

    @Override
    public List<OrderDTO> getOrders(String username) {
        return orderRepository.findAllByUsername(username)
                .stream()
                .map(orderMapper::straightMapping)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(String code) throws Exception {
        Order order = Optional.ofNullable(orderRepository.findByCode(code)).orElseThrow(() -> new Exception("Order is not found"));
        List<OrderEntry> orderEntries = order.getOrderEntries();
        orderEntries.forEach(orderEntry -> {
            orderEntryRepository.delete(orderEntry);
            productClient.exchange(orderEntry.getCode(), (orderEntry.getCount() * -1));
        });
        orderRepository.delete(order);
    }

    private ProductDTO readProductDTO(Response response) throws IOException {
        return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(IOUtils.toString(response.body().asReader()), ProductDTO.class);
    }

}
