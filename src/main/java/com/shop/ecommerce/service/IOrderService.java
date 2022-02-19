package com.shop.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.shop.ecommerce.model.Order;
import com.shop.ecommerce.model.User;

public interface IOrderService {
	List<Order> findAll();

	Optional<Order> findById(Integer id);

	Order save(Order order);

	String generateNumberOrder();

	List<Order> findByUser(User user);

}
