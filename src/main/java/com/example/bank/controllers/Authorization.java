package com.example.bank.controllers;

import com.example.bank.model.Role;
import com.example.bank.model.Status;
import com.example.bank.model.User;
import com.example.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Authorization {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String openLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String openRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute(name = "user") User user){
        user.setPin(new BCryptPasswordEncoder(12).encode(user.getPin()));
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        user.setBalance(0);
        userRepository.save(user);
        return "redirect:/";
    }
}
