package com.shop.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.ecommerce.model.Order;
import com.shop.ecommerce.repository.IOrderRepository;

@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private IOrderRepository orderRepository;

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}
	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	// 0000010
	public String generateNumberOrder() {
		int number=0;
		String numberConcatenated="";

		List<Order> orders = findAll();

		List<Integer> numbers= new ArrayList<Integer>();

		orders.stream().forEach(o -> numbers.add( Integer.parseInt( o.getNumber())));

		if (orders.isEmpty()) {
			number=1;
		}else {
			number= numbers.stream().max(Integer::compare).get();
			number++;
		}

		if (number<10) { //0000001000
			numberConcatenated="000000000"+String.valueOf(number);
		}else if(number<100) {
			numberConcatenated="00000000"+String.valueOf(number);
		}else if(number<1000) {
			numberConcatenated="0000000"+String.valueOf(number);
		}else if(number<10000) {
			numberConcatenated="0000000"+String.valueOf(number);
		}		

		return numberConcatenated;
	}

}
