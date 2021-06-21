package com.example.bank.services;

import com.example.bank.model.Transaction;

import java.util.List;

public interface TransactionsService {
    List<Transaction> findByCardTo(String card);
    List<Transaction> findByCardFrom(String card);
}
