package com.example.relationship.controller;

import com.example.relationship.api_model.CreateTransactionRequest;
import com.example.relationship.dto.TransactionDTO;
import com.example.relationship.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/wallets/{walletId}/transactions")
    public TransactionDTO createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest){
        return transactionService.createTransaction(createTransactionRequest);
    }

    @GetMapping("/wallets/{walletId}/transactions")
    public List<TransactionDTO> findAllTransactions(){
        return transactionService.findAllTransactions();
    }

    @GetMapping("/wallets/{walletId}/transactions/{transactionId}")
    public TransactionDTO findById(@PathVariable Long transactionId){
        return transactionService.findById(transactionId);
    }

    @PutMapping("/wallets/{walletId}/transactions/{transactionId}")
    public TransactionDTO updateTransaction(@RequestBody CreateTransactionRequest createTransactionRequest, @PathVariable Long transactionId){
        return transactionService.updateTransaction(createTransactionRequest, transactionId);
    }

    @DeleteMapping("/wallets/{walletId}/transactions/{transactionId}")
    public String deleteById(@PathVariable Long transactionId){
        return transactionService.deleteById(transactionId);
    }
}
