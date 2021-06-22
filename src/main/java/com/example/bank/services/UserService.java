package com.example.bank.services;

import com.example.bank.errors_handler.errors.ApiRequestException;
import com.example.bank.errors_handler.errors.ApiUserNotFoundException;
import com.example.bank.model.entities.User;
import com.example.bank.model.rest_response.AllTransactionsResponse;

import java.math.BigDecimal;
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
    void topUp(String card, BigDecimal value) throws ApiUserNotFoundException;

    /**
     * Make transaction between two cards
     * @param cardFrom
     * @param cardTo
     * @param value
     */
    long transact(String cardFrom, String cardTo, BigDecimal value) throws ApiUserNotFoundException, ApiRequestException;

    /**
     * Take money from card
     * @param card
     * @param value
     */
    void takeMoney(String card, BigDecimal value) throws ApiUserNotFoundException, ApiRequestException;

    /**
     * Creates new user with default values
     * and saves it to database
     * @param user
     */
    void createNewUser(User user);
}
