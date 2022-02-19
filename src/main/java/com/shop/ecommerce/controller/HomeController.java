package com.shop.ecommerce.controller;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import com.shop.ecommerce.model.User;
import com.shop.ecommerce.service.IDetailOrderService;
import com.shop.ecommerce.service.IOrderService;
import com.shop.ecommerce.service.IUserService;
import com.shop.ecommerce.service.ProductService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderService orderService;

	@Autowired
	private IDetailOrderService detalleOrderService;

	List<OrderDetail> details = new ArrayList<OrderDetail>();

	Order order = new Order();

	@GetMapping("")
	public String home(Model model, HttpSession session) {
		log.info("User: {}", session.getAttribute("iduser"));
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
		log.info("Produto adicionado: {}", optionalProduct.get());
		log.info("Quantidade: {}", quantity);
		product = optionalProduct.get();

		detailOrder.setQuantity(quantity);
		detailOrder.setPrice(product.getPrice());
		detailOrder.setName(product.getName());
		detailOrder.setTotal(product.getPrice() * quantity);
		detailOrder.setProduct(product);

		Integer idProduct = product.getId();
		boolean accessed = details.stream().anyMatch(p -> p.getProduct().getId() == idProduct);

		if (!accessed) {
			details.add(detailOrder);
		}

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

	@GetMapping("/getCart")
	public String getCart(Model model) {

		model.addAttribute("cart", details);
		model.addAttribute("order", order);
		return "/user/car";
	}

	@GetMapping("/order")
	public String order(Model model, HttpSession session) {
		User user = userService.findById( Integer.parseInt(session.getAttribute("iduser").toString())).get();

		model.addAttribute("cart", details);
		model.addAttribute("order", order);
		model.addAttribute("user", user);

		return "user/orderresume";
	}

	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
		Date created_at = new Date();
		order.setCreated_at(created_at);
		order.setNumber(orderService.generateNumberOrder());

		User user = userService.findById( Integer.parseInt(session.getAttribute("iduser").toString())  ).get();

		order.setUser(user);
		orderService.save(order);

		for (OrderDetail dt : details) {
			dt.setOrder(order);
			detalleOrderService.save(dt);
		}

		order = new Order();
		details.clear();

		return "redirect:/";
	}

	@PostMapping("/search")
	public String searchProduct(@RequestParam String name, Model model) {
		log.info("Nome do produto: {}", name);
		List<Product> products = productService.findAll().stream().filter(p -> p.getName().contains(name))
				.collect(Collectors.toList());
		model.addAttribute("products", products);
		return "user/home";
	}

}
