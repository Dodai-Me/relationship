package com.example.relationship.service;
import com.example.relationship.api_model.CreateWalletRequest;
import com.example.relationship.dto.WalletDTO;
import com.example.relationship.entity.Wallet;
import com.example.relationship.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WalletServiceIntegrationTest {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletService walletService;

    private Long savedWalletId;

    @BeforeEach
    void setUp(){
        walletRepository.deleteAll();

        Wallet wallet = new Wallet();
        wallet.setBalance(23424L);
        wallet.setUuid(UUID.randomUUID().toString());

        Wallet savedWallet = walletRepository.save(wallet);
        this.savedWalletId = savedWallet.getId();
    }

    @Test
    void updateWalletBalanceTakesEffect(){
        CreateWalletRequest updateRequest = new CreateWalletRequest();
        updateRequest.setBalance(73273273L);

        WalletDTO walletDTO = walletService.updateWallet(savedWalletId, updateRequest);

        assertNotNull(walletDTO);
        assertEquals(savedWalletId, walletDTO.getId());
        assertEquals(73273273L, walletDTO.getBalance());

        Wallet databseWallet = walletRepository.findById(savedWalletId).orElseThrow(() -> new AssertionError("Wallet was deleted. Could not be found in DB"));

        assertEquals(73273273L, databseWallet.getBalance());
    }

}