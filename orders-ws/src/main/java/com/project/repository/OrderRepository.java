package com.project.repository;

import com.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    Order findByCode(String code);

    List<Order> findAllByUsername(String username);
}
