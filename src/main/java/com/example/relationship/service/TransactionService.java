package com.example.relationship.service;

import com.example.relationship.api_model.CreateTransactionRequest;
import com.example.relationship.repository.TransactionRepository;
import com.example.relationship.repository.WalletRepository;
import com.example.relationship.dto.TransactionDTO;
import com.example.relationship.entity.Transaction;
import com.example.relationship.entity.Wallet;
import com.example.relationship.enums.TransactionType;
import com.example.relationship.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(WalletRepository walletRepository, TransactionRepository transactionRepository){
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO createTransaction(CreateTransactionRequest createTransactionRequest){
        Wallet wallet = new Wallet();
        Transaction transaction = new Transaction();
        transaction.setAmount(createTransactionRequest.getAmount());
        transaction.setTransactionType(createTransactionRequest.getTransactionType());

        Optional<Wallet> optionalWallet = walletRepository.findById(createTransactionRequest.getWalletId());

        if(optionalWallet.isPresent()){
            wallet = optionalWallet.get();
            transaction.setWallet(wallet);
        }

        if(wallet.getBalance() < createTransactionRequest.getAmount() && createTransactionRequest.getTransactionType() == TransactionType.WITHDRAWAL){
            throw new EntityNotFoundException("Insufficient Balance");
        }

        if(createTransactionRequest.getTransactionType() == TransactionType.DEPOSIT){
            Long totalAmount = wallet.getBalance() + createTransactionRequest.getAmount();
            wallet.setBalance(totalAmount);
            walletRepository.save(wallet);
        }

        if (createTransactionRequest.getTransactionType() == TransactionType.WITHDRAWAL){
            Long totalAmount = wallet.getBalance() - createTransactionRequest.getAmount();
            wallet.setBalance(totalAmount);
            walletRepository.save(wallet);
        }

        transactionRepository.save(transaction);
        return transactionToTransactionDTO(transaction);
    }

    public List<TransactionDTO> findAllTransactions(){
        return transactionRepository.findAll().stream().map(this::transactionToTransactionDTO).toList();
    }

    public TransactionDTO findById(Long id){
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);

        if(optionalTransaction.isPresent()){
            return transactionToTransactionDTO(optionalTransaction.get());
        }

        throw new EntityNotFoundException("Could not find Transaction ID " + id);
    }

    public String deleteById(Long id){
        transactionRepository.deleteById(id);
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
