package com.project.controller;

import com.project.dto.OrderDTO;
import com.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String status(){
        return "Working on port " + env.getProperty("local.server.port")
                + " with config environment = " + env.getProperty("token.check")
                + "\n" + "Test value: " + env.getProperty("token.test");
    }

    @GetMapping("/order")
    public ResponseEntity<OrderDTO> getOrder(@RequestParam String code) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(code));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam String username){
        List<OrderDTO> orders = orderService.getOrders(username);
        if (orders.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) throws Exception {
        if (orderService.controlOrderEntries(orderDTO.getOrderEntries())) {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.placeOrder(orderDTO));
        } else
            throw new Exception("Not enough stock");
    }

    @DeleteMapping
    public ResponseEntity<String> cancelOrder(@RequestParam String code) throws Exception {
        orderService.cancelOrder(code);
        return ResponseEntity.status(HttpStatus.OK).body("Order has been canceled successfully!");
    }

}
