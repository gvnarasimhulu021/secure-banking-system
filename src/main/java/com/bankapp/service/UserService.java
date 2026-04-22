package com.bankapp.service;

import java.util.List;

import com.bankapp.entity.User;

public interface UserService {

	User registerUser(User user);

	User login(String email, String password);

	User deposit(String email, double amount);

	User withdraw(String email, double amount);

	double getBalance(String email);

	List<User> getAllUsers();

	User getUserByEmail(String email);

	User updateBalance(String email, double amount);

	void deleteUser(Long id);

	void transfer(String fromEmail, String toEmail, double amount);
}
