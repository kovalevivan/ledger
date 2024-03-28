package org.example.domain;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Account {

    private UUID id;
    private AtomicLong balance;

    public Account(UUID id, Long balance) {
        this.id = id;
        this.balance = new AtomicLong(balance);
    }

    public UUID getId() {
        return id;
    }

    public Long getBalance() {
        return balance.get();
    }

    public void add(Long delta) {
        balance.addAndGet(delta);
    }
}
