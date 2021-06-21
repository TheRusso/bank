package com.example.bank.controllers;

import com.example.bank.errors_handler.errors.ApiUserNotFoundException;
import com.example.bank.model.rest_response.AllTransactionsResponse;
import com.example.bank.model.rest_response.UserResponse;
import com.example.bank.services.UserService;
import com.example.bank.utils.ErrorKeys;
import com.example.bank.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private UserService userService;

    @GetMapping("/balance")
    public UserResponse getBalance(Principal principal){
        var user = userService.findByCard(principal.getName()).orElseThrow(()->{
            throw new ApiUserNotFoundException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.CANT_FIND_USER.getKey()));
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
