package com.shop.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.shop.ecommerce.service.IUserService;
import com.shop.ecommerce.model.Order;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.service.IOrderService;
import com.shop.ecommerce.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	@Autowired
	private ProductService productService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderService ordersService;
	private Logger logg= LoggerFactory.getLogger(AdministratorController.class);
	
	@GetMapping("")
	public String home(Model model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "administrator/home";
	}
	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "administrator/users";
	}
	@GetMapping("/orders")
	public String orders(Model model) {
		model.addAttribute("orders", ordersService.findAll());
		return "administrator/orders";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable Integer id) {
		logg.info("Id da entrega {}",id);
		Order order= ordersService.findById(id).get();

		model.addAttribute("details", order.getDetail());

		return "administrator/orderdetail";
	}


}
