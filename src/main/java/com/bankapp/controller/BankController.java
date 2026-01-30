package com.bankapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @GetMapping("/api/bank/profile")
    public String profile() {
        return "Welcome to Secure Banking System 🔐";
    }
}
