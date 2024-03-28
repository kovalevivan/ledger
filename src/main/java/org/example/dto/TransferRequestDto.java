package org.example.dto;

import java.util.Objects;
import java.util.UUID;

public class TransferRequestDto {
    private final UUID accountFrom;
    private final UUID accountTo;
    private final Long amount;

    public TransferRequestDto(UUID accountFrom, UUID accountTo, Long amount) {
        this.accountFrom = Objects.requireNonNull(accountFrom, "accountFrom must not be null");
        this.accountTo = Objects.requireNonNull(accountTo, "accountTo must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
    }

    public UUID getAccountFrom() {
        return accountFrom;
    }

    public UUID getAccountTo() {
        return accountTo;
    }


    public Long getAmount() {
        return amount;
    }

}
