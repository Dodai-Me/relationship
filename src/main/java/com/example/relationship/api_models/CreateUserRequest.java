package com.example.relationship.api_models;

import lombok.Data;

@Data
public class CreateUserRequest {
    String firstName;
    String lastName;
    Long walletId;
}
