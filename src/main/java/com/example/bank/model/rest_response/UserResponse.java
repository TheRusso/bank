package com.example.bank.model.rest_response;

import com.example.bank.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String card;
    private BigDecimal balance;

    public UserResponse(User user) {
        this.card = user.getCard();
        this.balance = user.getBalance();
    }
}
