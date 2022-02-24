package com.shop.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.shop.ecommerce.service.IUserService;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.service.ProductService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	@Autowired
	private ProductService productService;
	@Autowired
	private IUserService userService;
	
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

}
