package com.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bankapp.entity.User;
import com.bankapp.service.TransactionService;
import com.bankapp.service.UserService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private UserService userService;
	@Autowired
	private TransactionService transactionService;

	// 💰 Deposit
	@PostMapping("/deposit")
	public User deposit(@RequestParam double amount) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userService.deposit(email, amount);

		transactionService.saveTransaction(email, "DEPOSIT", amount); // 🔥

		return user;
	}

	// 💸 Withdraw
	@PostMapping("/withdraw")
	public User withdraw(@RequestParam double amount) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userService.withdraw(email, amount);

		transactionService.saveTransaction(email, "WITHDRAW", amount); // 🔥

		return user;
	}

	// 📊 Balance
	@GetMapping("/balance")
	public double getBalance() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return userService.getBalance(email);
	}

	@GetMapping("/details")
	public User getAccountDetails() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userService.getUserByEmail(email);

		user.setPassword(null); // 🔥 IMPORTANT

		return user;
	}

	@PostMapping("/transfer")
	public String transfer(@RequestParam String toEmail, @RequestParam double amount) {

		String fromEmail = SecurityContextHolder.getContext().getAuthentication().getName();

		userService.transfer(fromEmail, toEmail, amount);

		return "Transfer successful";
	}
}