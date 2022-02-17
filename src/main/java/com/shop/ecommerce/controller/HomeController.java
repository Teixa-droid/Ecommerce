package com.shop.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.ecommerce.model.Order;
import com.shop.ecommerce.model.OrderDetail;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.service.ProductService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductService productService;

	List<OrderDetail> details = new ArrayList<OrderDetail>();

	Order order = new Order();

	@GetMapping("")
	public String home(Model model) {

		model.addAttribute("products", productService.findAll());

		return "user/home";
	}

	@GetMapping("producthome/{id}")
	public String productHome(@PathVariable Integer id, Model model) {
		log.info("Id product enviado como parámetro {}", id);
		Product product = new Product();
		Optional<Product> productOptional = productService.get(id);
		product = productOptional.get();

		model.addAttribute("product", product);

		return "user/producthome";
	}

	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer quantity, Model model) {
		OrderDetail detailOrder = new OrderDetail();
		Product product = new Product();
		double sumTotal = 0;

		Optional<Product> optionalProduct = productService.get(id);
		log.info("Product añadido: {}", optionalProduct.get());
		log.info("Cantidad: {}", quantity);
		product = optionalProduct.get();

		detailOrder.setQuantity(quantity);
		detailOrder.setPrice(product.getPrice());
		detailOrder.setName(product.getName());
		detailOrder.setTotal(product.getPrice() * quantity);
		detailOrder.setProduct(product);

		details.add(detailOrder);

		sumTotal = details.stream().mapToDouble(dt -> dt.getTotal()).sum();

		order.setTotal(sumTotal);
		model.addAttribute("cart", details);
		model.addAttribute("order", order);

		return "user/car";
	}

	@GetMapping("/delete/cart/{id}")
	public String deleteProductCart(@PathVariable Integer id, Model model) {

		
		List<OrderDetail> ordersNew = new ArrayList<OrderDetail>();

		for (OrderDetail detailOrder : details) {
			if (detailOrder.getProduct().getId() != id) {
				ordersNew.add(detailOrder);
			}
		}

		details = ordersNew;

		double sumTotal = 0;
		sumTotal = details.stream().mapToDouble(dt -> dt.getTotal()).sum();

		order.setTotal(sumTotal);
		model.addAttribute("cart", details);
		model.addAttribute("order", order);

		return "user/car";
	}

}
