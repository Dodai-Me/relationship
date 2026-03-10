package com.example.relationship.dto;

import com.example.relationship.entity.Wallet;
import lombok.Data;

@Data
public class UserDTO {
    Long id;
    String uuid;
    String firstName;
    String lastName;
    Wallet wallet;
}
