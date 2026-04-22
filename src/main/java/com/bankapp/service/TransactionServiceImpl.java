package com.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.entity.Transaction;
import com.bankapp.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repo;

    @Override
    public void saveTransaction(String email, String type, double amount) {
        Transaction tx = new Transaction(email, type, amount);
        repo.save(tx);
    }

    @Override
    public List<Transaction> getTransactions(String email) {
        return repo.findByEmail(email);
    }
}