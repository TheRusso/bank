package com.example.bank.controllers;

import com.example.bank.errors_handler.errors.ApiRequestException;
import com.example.bank.errors_handler.errors.ApiUserNotFoundException;
import com.example.bank.model.entities.User;
import com.example.bank.model.rest_response.AllTransactionsResponse;
import com.example.bank.model.rest_response.TransactionResponse;
import com.example.bank.model.rest_response.UserResponse;
import com.example.bank.repositories.TransactionsRepository;
import com.example.bank.services.UserService;
import com.example.bank.utils.ErrorKeys;
import com.example.bank.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionsRepository transactionsRepository;

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

    @GetMapping("/transactions/{id}")
    public TransactionResponse getTransaction(@PathVariable(name = "id") long id){
        var transaction = transactionsRepository.findById(id).orElseThrow(() -> {throw new ApiUserNotFoundException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.CANT_FIND_USER.getKey()));});
        return new TransactionResponse().builder()
                .card(transaction.getUserTo().getCard())
                .value(transaction.getValue())
                .build();
    }

    @PostMapping("/topup")
    public void topUp(@ModelAttribute(name = "card") String card,
                      @ModelAttribute(name = "value") String value,
                      HttpServletResponse response){
        userService.topUp(card, new BigDecimal(value));

        sendToBalancePage(response);
    }

    @PostMapping("/make_transaction")
    public void transaction(@ModelAttribute(name = "card") String card,
                            @ModelAttribute(name = "value") String value,
                            Principal principal,
                            HttpServletResponse response){
        if (card.equals(principal.getName()))
            throw new ApiRequestException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.BAD_REQUEST.getKey()));

        long transactionId = userService.transact(principal.getName(), card, new BigDecimal(value));

        try {
            response.sendRedirect("balance");
        } catch (IOException e) {
            throw new ApiRequestException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.BAD_REQUEST.getKey()));
        }
    }

    @PostMapping("/take_money")
    public void getMoney(@ModelAttribute(name = "value") String value,
                         Principal principal,
                         HttpServletResponse response){
        userService.takeMoney(principal.getName(), new BigDecimal(value));

        sendToBalancePage(response);
    }

    private void sendToBalancePage(HttpServletResponse response){
        try {
            response.sendRedirect("balance");
        } catch (IOException e) {
            throw new ApiRequestException(ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.BAD_REQUEST.getKey()));
        }
    }


}
