package com.example.bank.services;

import com.example.bank.model.entities.Transaction;

import java.util.List;

public interface TransactionsService {
    /**
     * Returns List of input Transactions by given card
     * @param card
     * @return List of Transactions
     */
    List<Transaction> findByCardTo(String card);

    /**
     * Returns List of output Transactions by given card
     * @param card
     * @return List of Transactions
     */
    List<Transaction> findByCardFrom(String card);
}
