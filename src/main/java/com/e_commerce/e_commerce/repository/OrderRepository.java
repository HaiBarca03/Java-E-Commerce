package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.Order;
import com.e_commerce.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
    List<Order> findByUserId(String userId);
    List<Order> findByUser(User user);
}
