package com.example.bank.services;

import com.example.bank.model.entities.User;
import com.example.bank.model.rest_response.AllTransactionsResponse;

import java.util.Optional;

public interface UserService {
    /**
     * Returns Optional<User> by card
     * @param card
     * @return Optional<User>
     *              User entity
     */
    Optional<User> findByCard(String card);

    /**
     * Returns Optional<User> by id
     * @param id
     * @return Optional<User>
     *              User entity
     */
    Optional<User> findById(long id);

    /**
     * Saves or updates user entity
     * @param user
     *          User entity
     */
    void save(User user);

    /**
     * Returns AllTransactionsResponse
     * @param card
     * @return AllTransactionsResponse
     *          using for showing transactions in REST
     */
    AllTransactionsResponse getAllTransactionsResponseByCard(String card);

    /**
     * Top up money in the account using cash
     * @param card
     * @param value
     */
    void topUp(String card, long value);

    /**
     * Make transaction between two cards
     * @param cardFrom
     * @param cardTo
     * @param value
     */
    void transact(String cardFrom, String cardTo, long value);
}
