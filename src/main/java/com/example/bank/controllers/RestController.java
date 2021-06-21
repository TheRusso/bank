package com.example.bank.controllers;

import com.example.bank.model.rest_response.AllTransactionsResponse;
import com.example.bank.model.rest_response.TransactionResponse;
import com.example.bank.model.rest_response.UserResponse;
import com.example.bank.repositories.TransactionsRepository;
import com.example.bank.services.TransactionsService;
import com.example.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/balance")
    public UserResponse getBalance(Principal principal){
        var user = userService.findByCard(principal.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("Something went wrong");
        });

        return UserResponse.builder()
                .card(user.getCard())
                .balance(user.getBalance())
                .build();
    }

    @GetMapping("/transactions")
    public AllTransactionsResponse getAllTransactions(Principal principal){
        return userService.getAllTransactionsResponseByCard(principal.getName());
    }
}
