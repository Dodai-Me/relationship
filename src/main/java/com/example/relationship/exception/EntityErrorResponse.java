package com.example.relationship.exception;

import lombok.Data;

@Data
public class EntityErrorResponse {
    private int status;
    private String message;
}
