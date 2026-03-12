package com.example.relationship.entity;

import com.example.relationship.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "amount")
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType transactionType;

    @JoinColumn(name = "wallet_id")
    @ManyToOne
    private Wallet wallet;

    @Column(name = "created_at")
    private LocalDateTime localDateTime;

    public Transaction(){}

    public Transaction(Long amount, Wallet wallet, LocalDateTime localDateTime){
        this.amount = amount;
        this.wallet = wallet;
        this.localDateTime = localDateTime;
    }

    @PrePersist
    protected void onCreate(){
        this.uuid = UUID.randomUUID().toString();
        this.localDateTime = LocalDateTime.now();
    }

}