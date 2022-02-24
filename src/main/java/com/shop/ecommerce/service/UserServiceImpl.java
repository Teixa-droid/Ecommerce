package com.shop.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.ecommerce.model.User;
import com.shop.ecommerce.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
