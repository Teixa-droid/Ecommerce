package com.shop.ecommerce.service;

import java.util.List;

import com.shop.ecommerce.model.Order;

public interface IOrderService {
	List<Order> findAll();
	Order save (Order order);

}
