package com.example.bank.services.service_impl;

import com.example.bank.model.User;
import com.example.bank.model.rest_response.AllTransactionsResponse;
import com.example.bank.model.rest_response.TransactionResponse;
import com.example.bank.repositories.TransactionsRepository;
import com.example.bank.repositories.UserRepository;
import com.example.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Optional<User> findByCard(String card) {
        return userRepository.findByCard(card);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    public AllTransactionsResponse getAllTransactionsResponseByCard(String card){
        var allTransactionsResponse = new AllTransactionsResponse();

        allTransactionsResponse.setInput(transactionsRepository.findByCardFrom(card)
                .stream().map(o -> new TransactionResponse(o.getUserFrom().getCard(), o.getValue()))
                .collect(Collectors.toList()));

        allTransactionsResponse.setOutput(transactionsRepository.findByCardTo(card)
                .stream().map(o -> new TransactionResponse(o.getUserFrom().getCard(), o.getValue()))
                .collect(Collectors.toList()));

        return allTransactionsResponse;
    }
}
