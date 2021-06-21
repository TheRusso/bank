package com.example.bank.services;

import com.example.bank.model.entities.User;
import com.example.bank.model.rest_response.AllTransactionsResponse;

import java.util.Optional;

public interface UserService {
    Optional<User> findByCard(String card);
    Optional<User> findById(long id);
    void save(User user);
    AllTransactionsResponse getAllTransactionsResponseByCard(String card);
    void topUp(String card, long value);
    void transact(String cardFrom, String cardTo, long value);
}
