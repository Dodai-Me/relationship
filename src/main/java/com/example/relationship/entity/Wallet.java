package com.example.relationship.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@Setter
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "balance")
    private Long balance;

    public Wallet(){

    }

    public Wallet(Long balance){
        this.balance = balance;
    }

    @PrePersist
    protected void onCreate(){
        this.uuid = UUID.randomUUID().toString();
    }
}
