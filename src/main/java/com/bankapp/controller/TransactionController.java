package com.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bankapp.entity.Transaction;
import com.bankapp.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return service.getTransactions(email);
    }
}