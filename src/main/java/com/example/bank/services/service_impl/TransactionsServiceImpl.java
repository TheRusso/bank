package com.example.bank.services.service_impl;

import com.example.bank.model.Transaction;
import com.example.bank.repositories.TransactionsRepository;
import com.example.bank.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public List<Transaction> findByCardTo(String card) {
        return transactionsRepository.findByCardTo(card);
    }

    @Override
    public List<Transaction> findByCardFrom(String card) {
        return transactionsRepository.findByCardFrom(card);
    }
}
