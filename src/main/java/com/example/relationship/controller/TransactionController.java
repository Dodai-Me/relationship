package com.example.relationship.controller;

import com.example.relationship.api_model.CreateTransactionRequest;
import com.example.relationship.dto.TransactionDTO;
import com.example.relationship.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Transactions", description = "Transaction management endpoints")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/wallets/{walletId}/transactions")
    @Operation(summary = "Create a transaction", description = "Creates a single transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction created"),
            @ApiResponse(responseCode = "404", description = "Transaction not created")
    })
    public TransactionDTO createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest){
        return transactionService.createTransaction(createTransactionRequest);
    }

    @GetMapping("/wallets/{walletId}/transactions")
    @Operation(summary = "Gets all transactions", description = "Returns all transactions by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions found"),
            @ApiResponse(responseCode = "404", description = "Transactions not found")
    })
    public List<TransactionDTO> findAllTransactions(@PathVariable String walletId){
        return transactionService.findAllTransactions();
    }

    @GetMapping("/wallets/{walletId}/transactions/{transactionId}")
    @Operation(summary = "Get transaction by Id", description = "Returns a single transaction by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public TransactionDTO findById(@PathVariable Long transactionId, @PathVariable String walletId){
        return transactionService.findById(transactionId);
    }

    @DeleteMapping("/wallets/{walletId}/transactions/{transactionId}")
    @Operation(summary = "Delete a wallet", description = "Deletes a single wallet by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet deleted"),
            @ApiResponse(responseCode = "404", description = "Wallet not deleted")
    })
    public String deleteByWalletId(@PathVariable Long walletId, @PathVariable Long transactionId){
        return transactionService.deleteTransaction(walletId, transactionId);
    }
}
