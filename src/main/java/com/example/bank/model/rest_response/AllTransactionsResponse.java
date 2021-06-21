package com.example.bank.model.rest_response;

import lombok.Data;

import java.util.List;

@Data
public class AllTransactionsResponse {
    private List<TransactionResponse> output;
    private List<TransactionResponse> input;
}
