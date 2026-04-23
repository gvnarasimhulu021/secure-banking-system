package com.bankapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankapp.entity.User;
import com.bankapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionService transactionService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public User registerUser(User user) {

		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("Email already registered...");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRole() == null || user.getRole().isEmpty()) {
			user.setRole("USER");
		}

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

	@Override
	public User deposit(String email, double amount) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		user.setBalance(user.getBalance() + amount);

		return userRepository.save(user);
	}

	@Override
	public User withdraw(String email, double amount) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (user.getBalance() < amount) {
			throw new RuntimeException("Insufficient balance");
		}

		user.setBalance(user.getBalance() - amount);

		return userRepository.save(user);
	}

	@Override
	public double getBalance(String email) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return user.getBalance();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public User updateBalance(String email, double amount) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		user.setBalance(amount);
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void transfer(String fromEmail, String toEmail, double amount) {

		User sender = userRepository.findByEmail(fromEmail).orElseThrow(() -> new RuntimeException("Sender not found"));

		User receiver = userRepository.findByEmail(toEmail)
				.orElseThrow(() -> new RuntimeException("Receiver not found"));

		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}

		if (sender.getBalance() < amount) {
			throw new RuntimeException("Insufficient balance");
		}

		// 💸 Deduct from sender
		sender.setBalance(sender.getBalance() - amount);

		// 💰 Add to receiver
		receiver.setBalance(receiver.getBalance() + amount);

		userRepository.save(sender);
		userRepository.save(receiver);

		// 🔥 IMPORTANT PART 
		transactionService.saveTransaction(fromEmail, "TRANSFER_SENT", amount);
		transactionService.saveTransaction(toEmail, "TRANSFER_RECEIVED", amount);
	}

}
