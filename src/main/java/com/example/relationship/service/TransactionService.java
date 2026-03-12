package com.example.relationship.service;

import com.example.relationship.api_model.CreateTransactionRequest;
import com.example.relationship.dao.TransactionDAO;
import com.example.relationship.dao.WalletDAO;
import com.example.relationship.dto.TransactionDTO;
import com.example.relationship.entity.Transaction;
import com.example.relationship.entity.Wallet;
import com.example.relationship.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    WalletDAO walletDAO;
    TransactionDAO transactionDAO;

    @Autowired
    public TransactionService(WalletDAO walletDAO, TransactionDAO transactionDAO){
        this.walletDAO = walletDAO;
        this.transactionDAO = transactionDAO;
    }

    public TransactionDTO createTransaction(CreateTransactionRequest createTransactionRequest){
        Transaction transaction = new Transaction();
        transaction.setAmount(createTransactionRequest.getAmount());
        transaction.setTransactionType(createTransactionRequest.getTransactionType());

        Optional<Wallet> optionalWallet = walletDAO.findById(createTransactionRequest.getWalletId());

        if(optionalWallet.isPresent()){
            Wallet wallet = optionalWallet.get();
            transaction.setWallet(wallet);
        }
        transactionDAO.save(transaction);
        return transactionToTransactionDTO(transaction);
    }

    public List<TransactionDTO> findAllTransactions(){
        return transactionDAO.findAll().stream().map(this::transactionToTransactionDTO).toList();
    }

    public TransactionDTO findById(Long id){
        Optional<Transaction> optionalTransaction = transactionDAO.findById(id);

        if(optionalTransaction.isPresent()){
            return transactionToTransactionDTO(optionalTransaction.get());
        }

        throw new EntityNotFoundException("Could not find Transaction ID " + id);
    }

    public TransactionDTO updateTransaction(CreateTransactionRequest createTransactionRequest, Long id){
        Optional<Transaction> optionalTransaction = transactionDAO.findById(id);
        Transaction transaction = new Transaction();
        if(optionalTransaction.isPresent()){
            transaction = optionalTransaction.get();
            transaction.setTransactionType(createTransactionRequest.getTransactionType());
            transaction.setAmount(createTransactionRequest.getAmount());
        }

        transactionDAO.save(transaction);
        return transactionToTransactionDTO(transaction);
    }

    public String deleteById(Long id){
        transactionDAO.deleteById(id);
        return "Transaction ID " + id + " has been deleted";
    }

    public TransactionDTO transactionToTransactionDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setId(transaction.getId());
        transactionDTO.setUuid(transaction.getUuid());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setLocalDateTime(transaction.getLocalDateTime());

        if(transaction.getWallet() != null){
            transactionDTO.setWalletId(transaction.getWallet().getId());
        }
        return transactionDTO;
    }
}
