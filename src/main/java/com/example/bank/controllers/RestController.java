package com.example.bank.controllers;

import com.example.bank.model.rest_response.UserResponse;
import com.example.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance")
    public UserResponse getBalance(Principal principal){
        var user = userRepository.findByCard(principal.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("Something went wrong");
        });
        return UserResponse.builder()
                .card(user.getCard())
                .balance(user.getBalance())
                .build();
    }
}
