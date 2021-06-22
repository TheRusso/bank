package com.example.bank.controllers;

import com.example.bank.model.entities.User;
import com.example.bank.model.rest_response.UserResponse;
import com.example.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Authorization {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@ModelAttribute(name = "card") String card,
                                 @ModelAttribute(name = "pin") String pin) {
        User user = new User().builder()
                .card(card)
                .pin(pin)
                .build();
        userService.createNewUser(user);
        return new UserResponse(user);
    }
}
