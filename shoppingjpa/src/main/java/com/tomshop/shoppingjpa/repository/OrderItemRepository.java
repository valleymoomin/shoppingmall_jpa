package com.tomshop.shoppingjpa.repository;

import com.tomshop.shoppingjpa.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
