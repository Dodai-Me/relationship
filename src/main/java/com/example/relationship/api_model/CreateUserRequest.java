package com.example.relationship.api_model;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private Long walletId;
}
