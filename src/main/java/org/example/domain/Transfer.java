package org.example.domain;

import java.util.UUID;

public class Transfer {

    private UUID id;
    private UUID accountFrom;
    private UUID accountTo;
    private Long amount;
    private TransferStatus status;

    public Transfer(UUID id, UUID accountFrom, UUID accountTo, Long amount) {
        this.id = id;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.status = TransferStatus.CREATED;
    }

    public void fail() {
        status = TransferStatus.FAILED;
    }

    public void complete() {
        status = TransferStatus.COMPLETED;
    }
}
