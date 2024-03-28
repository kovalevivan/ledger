package org.example.exception;

public class InsufficientFundException extends RuntimeException{

    public InsufficientFundException(String accountId) {
        super("Insufficient funds: " +  accountId);
    }
}
