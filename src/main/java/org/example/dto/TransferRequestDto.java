package org.example.dto;

import java.util.Objects;

public class TransferRequestDto {
    private final String accountFrom;
    private final String accountTo;
    private final Integer amount;

    public TransferRequestDto(String accountFrom, String accountTo, Integer amount) {
        this.accountFrom = Objects.requireNonNull(accountFrom, "accountFrom must not be null");
        this.accountTo = Objects.requireNonNull(accountTo, "accountTo must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }


    public Integer getAmount() {
        return amount;
    }

}
