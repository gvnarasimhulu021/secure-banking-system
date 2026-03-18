package com.bankapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bankapp.entity.User;
import com.bankapp.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // 👑 Dashboard
    @GetMapping("/dashboard")
    public String admin() {
        return "Welcome Admin 👑";
    }

    // 👀 Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 🔍 Get user by email
    @GetMapping("/user")
    public User getUser(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    // 💰 Update user balance
    @PutMapping("/update-balance")
    public User updateBalance(@RequestParam String email,
                             @RequestParam double amount) {
        return userService.updateBalance(email, amount);
    }

    // ❌ Delete user
    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}