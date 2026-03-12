package com.example.relationship.controller;

import com.example.relationship.api_model.CreateWalletRequest;
import com.example.relationship.dto.WalletDTO;
import com.example.relationship.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WalletController {

    WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public WalletDTO addWallet(@RequestBody CreateWalletRequest createWalletRequest){
        return walletService.addWallet(createWalletRequest);
    }

    @GetMapping("/wallets")
    public List<WalletDTO> findAllWallets(){
        return walletService.findAllWallets();
    }

    @GetMapping("/wallets/{walletId}")
    public WalletDTO findById(@PathVariable Long walletId){
        return walletService.findById(walletId);
    }

    @PutMapping("/wallets/{walletId}")
    public WalletDTO updateWallet(@PathVariable Long walletId, @RequestBody CreateWalletRequest createWalletRequest){
       return walletService.updateWallet(walletId, createWalletRequest);
    }

    @DeleteMapping("/wallets/{walletId}")
    public String deleteById(@PathVariable Long walletId){
       return walletService.deleteById(walletId);
    }
}