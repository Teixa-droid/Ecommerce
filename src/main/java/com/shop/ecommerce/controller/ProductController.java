package com.shop.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.model.User;
import com.shop.ecommerce.service.IUserService;
import com.shop.ecommerce.service.ProductService;
import com.shop.ecommerce.service.UploadFileService;
import com.shop.ecommerce.service.UserServiceImpl;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private UploadFileService upload;

	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("products", productService.findAll());
		return "products/show";
	}

	@GetMapping("/create")
	public String create() {
		return "products/create";
	}

	@PostMapping("/save")
	public String save(Product product, @RequestParam("img") MultipartFile file, HttpSession session)
			throws IOException {
		LOGGER.info("Este é o objeto do produto {}", product);
		User u = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get();
		product.setUser(u);

		// image
		if (product.getId() == null) {
			String nameImage = upload.saveImage(file);
			product.setImage(nameImage);
		} else {

		}

		productService.save(product);
		return "redirect:/products";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Product product = new Product();
		Optional<Product> optionalProduct = productService.get(id);
		product = optionalProduct.get();

		LOGGER.info("Product search: {}", product);
		model.addAttribute("product", product);

		return "products/edit";
	}

	@PostMapping("/update")
	public String update(Product product, @RequestParam("img") MultipartFile file) throws IOException {
		Product p = new Product();
		p = productService.get(product.getId()).get();
		if (file.isEmpty()) {

			product.setImage(p.getImage());
		} else {

			if (!p.getImage().equals("default.jpg")) {
				upload.deleteImage(p.getImage());
			}

			String nameImage = upload.saveImage(file);
			product.setImage(nameImage);
		}
		product.setUser(p.getUser());
		productService.update(product);
		return "redirect:/products";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		Product p = new Product();
		p = productService.get(id).get();

		if (!p.getImage().equals("default.jpg")) {
			upload.deleteImage(p.getImage());
		}
		productService.delete(id);
		return "redirect:/products";
	}

}