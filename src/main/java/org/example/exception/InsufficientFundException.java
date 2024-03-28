package org.example.exception;

import java.util.UUID;

public class InsufficientFundException extends RuntimeException{

    public InsufficientFundException(UUID accountId) {
        super("Insufficient funds: " +  accountId.toString());
    }
}
