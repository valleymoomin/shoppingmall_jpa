package com.tomshop.shoppingjpa.repository;

import com.tomshop.shoppingjpa.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
