package com.example.relationship.service;
import com.example.relationship.exception.EntityNotFoundException;
import com.example.relationship.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceUnitTest {

    @Mock
    WalletRepository walletRepository;

    @InjectMocks
    WalletService walletService;


    @Test
    void walletIdShouldBePositive() {
        Long invalidId = -5L;
        when(walletRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> walletService.findById(invalidId));
    }

    @Test
    void walletIdShouldNotBeNull(){
        when(walletRepository.findById(null)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> walletService.findById(null));
    }

}