package com.shop.ecommerce.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.ecommerce.model.OrderDetail;
import com.shop.ecommerce.repository.IDetailOrderRepository;

@Service
public class DetailOrderServiceImpl implements IDetailOrderService {
	@Autowired
	private IDetailOrderRepository detailOrderRepository;

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		return detailOrderRepository.save(orderDetail);
	}
}
