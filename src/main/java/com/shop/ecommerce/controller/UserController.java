package com.shop.ecommerce.controller;



import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.ecommerce.model.Order;
import com.shop.ecommerce.model.User;
import com.shop.ecommerce.service.IOrderService;
import com.shop.ecommerce.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IOrderService orderService;

	// /user/register
	@GetMapping("/register")
	public String create() {
		return "user/register";
	}

	@PostMapping("/save")
	public String save(User user) {
		logger.info("User register: {}", user);
		user.setType("USER");
		userService.save(user);
		return "redirect:/";
	}
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}

	@PostMapping("/access")
	public String acceder(User user, HttpSession session) {
		logger.info("Acce: {}", user);

		Optional<User> user1=userService.findByEmail(user.getEmail());
		//logger.info("User de db: {}", user.get());

		if (user1.isPresent()) {
			session.setAttribute("iduser", user1.get().getId());
			if (user1.get().getType().equals("ADMIN")) {
				return "redirect:/administrator";
			}else {
				return "redirect:/";
			}
		}else {
			logger.info("User nao existe");
		}

		return "redirect:/";
	}
	@GetMapping("/shopping")
	public String obtenerCompras(Model model, HttpSession session) {
		model.addAttribute("session", session.getAttribute("iduser"));
		User user= userService.findById(  Integer.parseInt(session.getAttribute("iduser").toString()) ).get();
		List<Order> orders= orderService.findByUser(user);
		logger.info("orders {}", orders);

		model.addAttribute("orders", orders);
		return "user/shopping";
	}

}
