package com.example.bank.services.service_impl;

import com.example.bank.errors_handler.errors.ApiRequestException;
import com.example.bank.errors_handler.errors.ApiUserNotFoundException;
import com.example.bank.model.entities.Transaction;
import com.example.bank.model.entities.User;
import com.example.bank.model.rest_response.AllTransactionsResponse;
import com.example.bank.model.rest_response.TransactionResponse;
import com.example.bank.repositories.TransactionsRepository;
import com.example.bank.repositories.UserRepository;
import com.example.bank.services.UserService;
import com.example.bank.utils.ErrorKeys;
import com.example.bank.utils.ErrorMessageUtil;
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

    @Override
    public void topUp(String card, long value) {
        var user = userRepository.findByCard(card).orElseThrow(() -> {throw new ApiUserNotFoundException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.CANT_FIND_USER.getKey()));});
        user.setBalance(user.getBalance() + value);

        userRepository.save(user);
    }

    @Override
    public void transact(String cardFrom, String cardTo, long value) throws ApiUserNotFoundException, ApiRequestException {
        var userFrom = userRepository.findByCard(cardFrom).orElseThrow(() -> {throw new UsernameNotFoundException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.CANT_FIND_USER.getKey()));});
        var userTo = userRepository.findByCard(cardTo).orElseThrow(() -> {throw new UsernameNotFoundException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.CANT_FIND_USER.getKey()));});

        if(userFrom.getBalance() < value)
            throw new ApiRequestException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.LOW_BALANCE.getKey()));

        userFrom.setBalance(userFrom.getBalance() - value);

        userTo.setBalance(userTo.getBalance() + value);

        var transaction = new Transaction().builder()
                .userFrom(userFrom)
                .userTo(userTo)
                .value(value)
                .build();

        transactionsRepository.save(transaction);
        userRepository.save(userFrom);
        userRepository.save(userTo);
    }

    @Override
    public void takeMoney(String card, long value) {
        var user = userRepository.findByCard(card).orElseThrow(() -> {throw new ApiUserNotFoundException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.CANT_FIND_USER.getKey()));});
        if(user.getBalance() < value)
            throw new ApiRequestException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.LOW_BALANCE.getKey()));

        user.setBalance(user.getBalance() - value);
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
