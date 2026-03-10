package com.example.relationship.dao;

import com.example.relationship.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletDAO extends JpaRepository<Wallet, Long> {}
