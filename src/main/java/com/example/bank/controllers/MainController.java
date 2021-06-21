package com.example.bank.controllers;

import com.example.bank.model.TopUpType;
import com.example.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String openMainPage(){
        return "index";
    }

    @GetMapping("/topup")
    public String openTopUpPage(){
        return "top_up";
    }

    @PostMapping("/topup")
    public String topUp(@ModelAttribute(name = "card") String card,
                        @ModelAttribute(name = "value") long value,
                        @ModelAttribute(name = "type") String type,
                        Principal principal){

        if(type.equals(TopUpType.CASH.name().toLowerCase()))
            userService.topUp(card, value);
        else if(type.equals(TopUpType.CARD.name().toLowerCase()))
            userService.transact(principal.getName(), card, value);
        else
            throw new IllegalArgumentException();

        return "redirect:/api/balance";
    }
}
