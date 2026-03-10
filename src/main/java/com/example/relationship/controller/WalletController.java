package com.example.relationship.controller;

import com.example.relationship.api_models.CreateWalletRequest;
import com.example.relationship.dao.WalletDAO;
import com.example.relationship.dto.WalletDTO;
import com.example.relationship.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WalletController {

    WalletDAO walletDAO;

    @Autowired
    public WalletController(WalletDAO walletDAO){
        this.walletDAO = walletDAO;
    }

    @PostMapping("/wallets")
    public WalletDTO create(@RequestBody CreateWalletRequest createWalletRequest){
        Wallet wallet = new Wallet();
        wallet.setBalance(createWalletRequest.getBalance());
        walletDAO.save(wallet);
        return walletToWalletDTO(wallet);
    }

    @GetMapping("/wallets")
    public List<WalletDTO> findAll(){
        return walletDAO.findAll().stream().map(this::walletToWalletDTO).toList();
    }

    @GetMapping("/wallets/{walletId}")
    public WalletDTO findById(@PathVariable Long walletId){
        Optional<Wallet> optionalWallet = walletDAO.findById(walletId);

        if(optionalWallet.isPresent()){
            return walletToWalletDTO(optionalWallet.get());
        }

        return new WalletDTO();
    }

    @PutMapping("/wallets/{walletId}")
    public WalletDTO updateWallet(@PathVariable Long walletId, @RequestBody CreateWalletRequest createWalletRequest){
        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = walletDAO.findById(walletId);

        if(optionalWallet.isPresent()){
            wallet = optionalWallet.get();
            wallet.setBalance(createWalletRequest.getBalance());
        }

        walletDAO.save(wallet);
        return walletToWalletDTO(wallet);
    }

    @DeleteMapping("/wallets/{walletId}")
    public String deleteById(@PathVariable Long walletId){
        walletDAO.deleteById(walletId);
        return "Wallet ID " + walletId + " has been deleted";
    }

    public WalletDTO walletToWalletDTO(Wallet wallet){
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setUuid(wallet.getUuid());
        walletDTO.setBalance(wallet.getBalance());
        return  walletDTO;
    }
}