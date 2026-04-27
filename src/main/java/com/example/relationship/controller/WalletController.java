package com.example.relationship.controller;

import com.example.relationship.api_model.CreateWalletRequest;
import com.example.relationship.dto.WalletDTO;
import com.example.relationship.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Wallets", description = "Wallet management endpoints")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    @Operation(summary = "Create a wallet", description = "Creates a single wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet created"),
            @ApiResponse(responseCode = "404", description = "Wallet not created")
    })
    public WalletDTO addWallet(@RequestBody CreateWalletRequest createWalletRequest){
        return walletService.addWallet(createWalletRequest);
    }

    @GetMapping("/wallets")
    @Operation(summary = "Gets all wallets", description = "Returns all wallets of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallets found"),
            @ApiResponse(responseCode = "404", description = "Wallets not found")
    })
    public List<WalletDTO> findAllWallets(){
        return walletService.findAllWallets();
    }

    @GetMapping("/wallets/{walletId}")
    @Operation(summary = "Get wallet by Id", description = "Returns a single wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public WalletDTO findById(@PathVariable Long walletId){
        return walletService.findById(walletId);
    }

    @PutMapping("/wallets/{walletId}")
    @Operation(summary = "Updates a wallet by Id", description = "Returns an edited single wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet updated"),
            @ApiResponse(responseCode = "404", description = "Wallet not updated")
    })
    public WalletDTO updateWallet(@PathVariable Long walletId, @RequestBody CreateWalletRequest createWalletRequest){
       return walletService.updateWallet(walletId, createWalletRequest);
    }

    @DeleteMapping("/wallets/{walletId}")
    @Operation(summary = "Delete wallet by Id", description = "Deletes a single wallet by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet deleted"),
            @ApiResponse(responseCode = "404", description = "Wallet not deleted")
    })
    public String deleteById(@PathVariable Long walletId){
       return walletService.deleteById(walletId);
    }
}