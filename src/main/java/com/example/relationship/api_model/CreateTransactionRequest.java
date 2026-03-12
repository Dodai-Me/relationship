package com.example.relationship.api_model;

import com.example.relationship.enums.TransactionType;
import lombok.Data;

@Data
public class CreateTransactionRequest {
    private Long amount;
    private TransactionType transactionType;
    private Long walletId;
}
