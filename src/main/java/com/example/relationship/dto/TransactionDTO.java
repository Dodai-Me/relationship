package com.example.relationship.dto;

import com.example.relationship.entity.Wallet;
import com.example.relationship.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private String uuid;
    private Long amount;
    private TransactionType transactionType;
    private LocalDateTime localDateTime;
    private Long walletId;
}
