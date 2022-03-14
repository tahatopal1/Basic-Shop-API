package com.project.repository;

import com.project.model.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntryRepository extends JpaRepository<OrderEntry, String> {
}
