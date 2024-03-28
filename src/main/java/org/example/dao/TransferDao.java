package org.example.dao;

import org.example.domain.Transfer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferDao {

    private static final Map<String, Transfer> transferPerId = new HashMap<>();

    public Transfer create(UUID accountFrom, UUID accountTo, Long amount) {
        final var id = UUID.randomUUID();
        final var transfer = new Transfer(id, accountFrom, accountTo, amount);
        transferPerId.put(id.toString(), transfer);
        return transfer;
    }

}
