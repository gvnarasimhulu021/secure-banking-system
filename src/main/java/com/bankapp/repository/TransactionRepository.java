package com.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankapp.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByEmail(String email);
}