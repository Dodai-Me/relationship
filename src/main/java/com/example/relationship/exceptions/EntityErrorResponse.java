package com.example.relationship.exceptions;

import lombok.Data;

@Data
public class EntityErrorResponse {
    private int status;
    private String message;
}
