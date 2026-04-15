package com.bankapp.service;

import com.bankapp.entity.Transaction;
import java.util.List;

public interface TransactionService {

    void saveTransaction(String email, String type, double amount);

    List<Transaction> getTransactions(String email);
}