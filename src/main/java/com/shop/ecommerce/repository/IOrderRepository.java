package com.shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.ecommerce.model.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

}
