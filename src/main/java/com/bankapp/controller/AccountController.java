package com.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bankapp.entity.User;
import com.bankapp.service.UserService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private UserService userService;

    // 💰 Deposit
    @PostMapping("/deposit")
    public User deposit(@RequestParam double amount) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userService.deposit(email, amount);
    }

    // 💸 Withdraw
    @PostMapping("/withdraw")
    public User withdraw(@RequestParam double amount) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userService.withdraw(email, amount);
    }

    // 📊 Balance
    @GetMapping("/balance")
    public double getBalance() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userService.getBalance(email);
    }
}