package com.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankapp.entity.User;
import com.bankapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public User registerUser(User user) {

	    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	        throw new RuntimeException("Email already registered");
	    }

	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    user.setRole("USER");
	    return userRepository.save(user);
	}


	@Override
	public User login(String email, String password) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Invalid email or password"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid email or password");
		}

		return user;
	}

}
