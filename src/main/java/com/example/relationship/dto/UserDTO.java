package com.example.relationship.dto;

import com.example.relationship.entity.Wallet;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String uuid;
    private String firstName;
    private String lastName;
    private Wallet wallet;
}
