package com.shop.ecommerce.service;

import java.util.Optional;

import com.shop.ecommerce.model.User;

public interface IUserService {
	Optional<User> findById(Integer id);
	User save (User user);
	Optional<User> findByEmail(String email);
}
