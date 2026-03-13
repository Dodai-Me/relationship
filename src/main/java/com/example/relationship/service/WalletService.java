package com.example.relationship.service;

import com.example.relationship.api_model.CreateWalletRequest;
import com.example.relationship.repository.WalletRepository;
import com.example.relationship.dto.WalletDTO;
import com.example.relationship.entity.Wallet;
import com.example.relationship.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    WalletService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    public WalletDTO addWallet(CreateWalletRequest createWalletRequest){
        Wallet wallet = new Wallet();
        wallet.setBalance(createWalletRequest.getBalance());
        walletRepository.save(wallet);
        return walletToWalletDTO(wallet);
    }

    public List<WalletDTO> findAllWallets(){
        return walletRepository.findAll().stream().map(this::walletToWalletDTO).toList();
    }

    public WalletDTO findById(Long id){
        Optional<Wallet> optionalWallet = walletRepository.findById(id);

        if(optionalWallet.isPresent()){
            return walletToWalletDTO(optionalWallet.get());
        }

        throw new EntityNotFoundException("Could not find Wallet ID " + id);
    }

    public WalletDTO updateWallet(Long id, CreateWalletRequest createWalletRequest){
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        Wallet wallet = new Wallet();

        if(optionalWallet.isPresent()){
            wallet = optionalWallet.get();
            wallet.setBalance(createWalletRequest.getBalance());
        }

        walletRepository.save(wallet);
        return walletToWalletDTO(wallet);
    }

    public String deleteById(Long id){
        walletRepository.deleteById(id);
        return "Wallet ID " + id + " has been deleted";
    }

   public WalletDTO walletToWalletDTO(Wallet wallet){
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setUuid(wallet.getUuid());
        walletDTO.setBalance(wallet.getBalance());
        return  walletDTO;
    }
}
